package se.iths.javafx.colorpicker.colorpicker;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.*;


public class Model {

    private final BooleanProperty inColor;
    private final ObjectProperty<Color> color;
    private ObjectProperty<Color> prevColor;
    private final DoubleProperty size;
    private DoubleProperty prevSize;


    ObservableList<Shape> shapes = FXCollections.observableArrayList();
    ToggleGroup toggleGroup = new ToggleGroup();
    Deque<Command> undo = new ArrayDeque<>();

    public Model() {
        this.inColor = new SimpleBooleanProperty();
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.prevColor = new SimpleObjectProperty<>();
        this.size = new SimpleDoubleProperty(20);
        this.prevSize = new SimpleDoubleProperty();
    }


    public DoubleProperty sizeProperty() {
        return size;
    }

    public Double getPrevSize() {
        return prevSize.get();
    }

    public Double getSize() {
        return size.get();
    }

    public void setSize(double size) {
        prevSize = this.size;
        this.size.set(size);
    }

    public void undo() {
        if (undo.isEmpty())
            return;
        Command command = undo.removeLast();
        command.execute();
    }


    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public Color getPrevColor() {
        return prevColor.get();
    }

    public Color getColor() {
        return color.get();
    }

    public void setColor(Color color) {
        prevColor = this.color;
        this.color.set(color);
    }

    public boolean isInColor() {
        return inColor.get();
    }

    public BooleanProperty inColorProperty() {
        return inColor;
    }

    public void setInColor(boolean inColor) {
        this.inColor.set(inColor);
    }

    public void svgShapes(StringBuilder svg) {
        for (var shape : shapes) {
            shape.convert(svg);
        }
    }


    public void shapeUndo() {
        if (shapes.size() - 1 >= 0)
            undo.addLast(() -> shapes.remove(shapes.size() - 1));
    }

}





