package collectionManager;

import collectionObjects.SpaceMarine;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeConverter implements Converter {
    @Override
    public boolean canConvert(Class type) {
        return ArrayDeque.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        ArrayDeque<SpaceMarine> deque = (ArrayDeque<SpaceMarine>) source;
        for (Object item : deque) {
            writer.startNode("item");
            context.convertAnother(item);
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            deque.add(context.convertAnother(deque, Object.class));
            reader.moveUp();
        }
        return deque;
    }
}