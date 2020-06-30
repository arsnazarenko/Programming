package client.servises;

import library.сlassModel.OrganizationType;
public class ObjectDataValidator {


    public boolean stringFieldValidate(String str) {
        if (str != null) {
            if (!str.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public boolean coordsXValidate(String str) {
        boolean flag = true;
        try{
            double res = Double.parseDouble(str);
            if(res <= - 98 || res >= 10000) {
                flag = false;
            }
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }
    }

    public boolean coordsYValidate(String str) {
        boolean flag = true;
        try{
            float res = Float.parseFloat(str);
            if(res <= - 148 || res >= 10000) {
                flag = false;
            }
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }
    }

    public boolean annualTurnoverValidate(String str) {
        boolean flag = true;
        try{
            if(str.equals("")) {
                return flag;
            }
            Double res = Double.parseDouble(str);
            if(res <= 0) {
                flag = false;
            }
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }

    }

    public boolean employeesCountValidate(String str) {
        boolean flag = true;
        try{
            int res = Integer.parseInt(str);
            if(res <= 0) {
                flag = false;
            }
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }
    }
    public boolean organizationTypeValidate(String str) {
        boolean flag = true;
        try{
            OrganizationType res = OrganizationType.valueOf(str);
            return flag;
        } catch (IllegalArgumentException e) {
            flag = false;
            return flag;
        }
    }

    public boolean zipCodeValidate(String str) {
        if(stringFieldValidate(str)) {
            if(str.length() >= 7) {
                return true;
            }
        }
        return false;
    }

    public boolean locationXValidate(String str) {
        boolean flag = true;
        try{
            double res = Long.parseLong(str);
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }
    }
    public boolean locationYValidate(String str) {
        boolean flag = true;
        try{
            double res = Double.parseDouble(str);
            return flag;
        } catch (NumberFormatException e) {
            flag = false;
            return flag;
        }
    }

    public boolean locationZValidate(String str) {
        return locationYValidate(str);
    }








}
