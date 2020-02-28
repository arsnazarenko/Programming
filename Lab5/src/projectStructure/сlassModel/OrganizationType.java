package projectStructure.сlassModel;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Класс,собержащий набор возможных типов Организации
 */
@XmlEnum
public enum OrganizationType {
    COMMERCIAL,
    GOVERNMENT,
    OPEN_JOINT_STOCK_COMPANY;


}
