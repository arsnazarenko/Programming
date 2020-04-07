package library.сlassModel;

import javax.xml.bind.annotation.XmlEnum;
import java.io.Serializable;

/**
 * Класс,собержащий набор возможных типов Организации
 */
@XmlEnum
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    GOVERNMENT,
    OPEN_JOINT_STOCK_COMPANY;
}
