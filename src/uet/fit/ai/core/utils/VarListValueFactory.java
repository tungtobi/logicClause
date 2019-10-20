package uet.fit.ai.core.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import uet.fit.ai.core.expr.ast.terminal.Variable;
import uet.fit.ai.core.truth.Interpretation;

import java.util.ListIterator;

public class VarListValueFactory implements Callback<TableColumn.CellDataFeatures<Interpretation, Integer>, ObservableValue<Integer>> {
    @Override
    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Interpretation , Integer> param) {

        int index = 0;

        ListIterator<TableColumn<Interpretation, ?>> iterator =
                param.getTableView().getColumns().listIterator();

        TableColumn tmpColumn;
        while(iterator.hasNext()) {
            tmpColumn = iterator.next();

            if(tmpColumn == param.getTableColumn())
                break;
            else
                index++;
        }

        Variable object = param.getValue().get(index);
        return new SimpleIntegerProperty(object.value ? 1 : 0).asObject();
    }
}