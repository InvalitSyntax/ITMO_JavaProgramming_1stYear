package app;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Адаптер для преобразования объектов типа {@link ZonedDateTime} в строку и обратно.
 * Используется для корректной сериализации и десериализации дат в формате XML.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    /**
     * Преобразует строку в объект типа {@link ZonedDateTime}.
     *
     * @param v строка для преобразования
     * @return объект {@link ZonedDateTime}
     * @throws Exception если строка имеет неверный формат
     */
    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return ZonedDateTime.parse(v, FORMATTER);
    }

    /**
     * Преобразует объект типа {@link ZonedDateTime} в строку.
     *
     * @param v объект {@link ZonedDateTime} для преобразования
     * @return строка в формате ISO_ZONED_DATE_TIME
     * @throws Exception если объект равен null
     */
    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.format(FORMATTER);
    }
}