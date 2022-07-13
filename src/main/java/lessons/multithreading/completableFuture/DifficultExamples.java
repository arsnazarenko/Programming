package lessons.multithreading.completableFuture;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DifficultExamples {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testCompose();
//        testPipelineWithHandle();
//        testCombine();
        testAllOf();


    }

    private static void testAllOf() throws InterruptedException, ExecutionException {
        /**
         * CompletableFuture.allOf() используется в тех случаях, когда есть список независимых задач, которые вы хотите запустить параллельно,
         * а после завершения всех задач выполнить какое-нибудь действие.
         */
        List<Integer> params = Arrays.asList(1, 23, 3, 43, 2, 1, 5, 6, 7, 8,23, 12, 12, 32, 32,22);

        // Асинхронно подсчитываем все зарплаты
        List<CompletableFuture<Integer>> salaries = params.stream()
                .map(param -> calculateSalary(param))
                .collect(Collectors.toList());

        // Создаём комбинированный Future, используя allOf()
        // Метод allOf возвращает CompletableFuture<Void>
        CompletableFuture<Void> allSalariesAsyncOperation = CompletableFuture.allOf(
                salaries.toArray(new CompletableFuture[0])
        );

        // Когда все задачи завершены, вызываем future.join(),
        // чтобы получить результаты и собрать их в список
        CompletableFuture<List<Integer>> allSalariesResult = allSalariesAsyncOperation.thenApply(v -> {
            return salaries.stream()
                    //  метод join аналогичен get, но бросает
                    //  unchecked исключение если  CompletableFuture завершается с ошибкой.
                    .map(CompletableFuture::join)
                    //  Этот CompletableFuture начнет выполнение только когда выполнится
                    //  CompletableFuture allSalariesAsyncOperation (Комбинированный CompletableFuture со списком задач)
                    //  А значит, что вызова join не будет вызывать длокировку потока, а просто вернет значение результата
                    .collect(Collectors.toList());
        });

        //Давайте теперь подсчитаем все полученные зарплаты и получим тройку лучших
        CompletableFuture<List<Integer>> top = allSalariesResult.thenApply(
                sal -> sal.stream()
                        .sorted(Comparator.reverseOrder())
                        .limit(3)
                        .collect(Collectors.toList())
        );
        top.get().forEach(s -> System.out.println("Зарплата: " + s + " рублей"));
    }

    static CompletableFuture<Integer> calculateSalary(Integer param) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                TimeUnit.SECONDS.sleep( (int) (Math.random() * 6 + 1));
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new IllegalStateException();
            }
            return 100000 + (int) (param * 1000 * Math.random());
        });
    }

    private static void testCombine() throws ExecutionException, InterruptedException {

        /**
         * thenCombine() используется, когда вы хотите, чтобы две задачи работали независимо друг от друга
         * и по завершению обоих выполнялось какое-нибудь действие.
         */
        CompletableFuture<Double> weightCf = CompletableFuture.supplyAsync(() -> {
            System.out.println("Получение веса в " + Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error with weight");
            }
            return 77.5;
        });

        CompletableFuture<Double> heightCf = CompletableFuture.supplyAsync(() -> {
            System.out.println("Получение роста в " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error with height");
            }

            return 183.5;
        });

        weightCf
                .thenCombine(heightCf, (weight, height) -> {

                    Double heightInMeter = height / 100;
                    return weight / (heightInMeter * heightInMeter);
                })
                .exceptionally(err -> {
                    err.printStackTrace();
                    return -1d;
                })
                .thenAccept(res -> System.out.println("Индекс массы тела: " + res)).get();
    }


    private static void testPipelineWithHandle() throws InterruptedException, ExecutionException {
        /**
         * Релизуем задачу с последовательным асинхронными запросами, которые передают друг другу результат
         * через методы класса CompletableFuture
         */

        List<User> usersData = init();
        UserService userService = UserService.build(usersData);
        CreditService creditService = CreditService.build(usersData);
        int a = 15;
        CompletableFuture<?> result = CompletableFuture
                .supplyAsync(() -> userService.getUserById(a))
                .thenApply(user -> creditService.getCreditRatingByUser(user))
                .handle((res, err) -> {
                            if (res != null) {
                                return res;
                            } else {
                                err.printStackTrace();
                                return null;
                            }
                        }
                );

        System.out.println(result.get());
    }

    private static void testCompose() throws InterruptedException, ExecutionException {
        /**
         * CompletableFuture.thenCompose()
         *
         *
         * В этом примере есть две функции которые асинхронно делают запрос к сервисам.
         * Мы хотим выполнить эти функции последовательно, чтобы вторая начала работу по завершению второй c передачей результата
         * НО передав в метод thenApply() Supplier, который уже возвращает CompletableFuture, мы получим воженный CompletableFuture
         * Метод thenCompose позволяет решить эту проблему
         * Правило таково: если функция-колбэк возвращает CompletableFuture,
         * а вы хотите простой результат, (а в большинстве случаев именно он вам и нужен), тогда используйте thenCompose().
         */
        List<User> usersData = init();
        UserService userService = UserService.build(usersData);
        CreditService creditService = CreditService.build(usersData);
        AsyncService asyncService = new AsyncService(userService, creditService);
        CompletableFuture<Double> result = asyncService.getUserDetail(3545)
                .thenCompose(user -> asyncService.getCreditRating(user))
                .handle((res, error) -> {
                    if (res != null) {
                        return res;
                    } else {
                        error.printStackTrace();
                        return null;
                    }
                });
        System.out.println(result.get());
    }


    static class AsyncService {

        private UserService userService;
        private CreditService creditService;

        public AsyncService(UserService userService, CreditService creditService) {
            this.userService = userService;
            this.creditService = creditService;
        }

        public CompletableFuture<User> getUserDetail(Integer userId) {
            return CompletableFuture.supplyAsync(() ->
                    userService.getUserById(userId));
        }

        public CompletableFuture<Double> getCreditRating(User user) {
            return CompletableFuture.supplyAsync(() ->
                    creditService.getCreditRatingByUser(user));
        }
    }


    static class User {

        private int id;
        private Role role;

        public User(int id, Role role) {
            this.id = id;
            this.role = role;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", role=" + role +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        enum Role {
            ADMIN_ROLE, USER_ROLE, PAID_ROLE, UNREGISTER_ROLE
        }

    }


    static class UserService {


        private Map<Integer, User> users;

        public static UserService build(List<User> usersData) {
            Map<Integer, User> users = usersData.stream().collect(Collectors.toMap(u -> u.id, u -> u));
            return new UserService(users);
        }

        private UserService(Map<Integer, User> users) {
            this.users = users;
        }

        public User getUserById(Integer id) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            User user = this.users.get(id);
            if (user == null) {
                throw new IllegalArgumentException("User with id + " + id + " not found");
            }
            return user;
        }
    }

    static class CreditService {

        private Map<User, Double> creditRatingDb;

        private CreditService(Map<User, Double> creditRatingDb) {
            this.creditRatingDb = creditRatingDb;
        }

        private static CreditService build(List<User> usersData) {
            Map<User, Double> creditRating = usersData.stream().collect(Collectors.toMap(u -> u, u -> (Math.random() * 4) + 1));
            return new CreditService(creditRating);
        }

        public Double getCreditRatingByUser(User user) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            Double rating = this.creditRatingDb.get(user);
            if (rating == null) {
                throw new IllegalArgumentException("User " + user + " not registered in this bank!");
            }
            return rating;
        }
    }

    private static List<User> init() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            User user = new User(i, User.Role.values()[(int) (Math.random() * 3)]);
            users.add(user);
        }
        return users;
    }
}
