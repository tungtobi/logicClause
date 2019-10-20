package uet.fit.ai.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import uet.fit.ai.core.Robinson;
import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.terminal.Variable;
import uet.fit.ai.core.expr.parser.RecursiveDescentParser;
import uet.fit.ai.core.pos.Term;
import uet.fit.ai.core.pos.TermGeneration;
import uet.fit.ai.core.truth.Interpretation;
import uet.fit.ai.core.truth.TruthTable;
import uet.fit.ai.core.utils.StringUtils;
import uet.fit.ai.core.utils.VarListValueFactory;

import java.util.List;

public class Controller {
    @FXML
    private ListView<IExpression> listPremise;
    @FXML
    private ListView<IExpression> listProof;
    @FXML
    private TextField txtNewPremise;
    @FXML
    private TextField txtNewProof;
    @FXML
    private Label labelResult;
    @FXML
    private Label txtPosForm;
    @FXML
    private TableView<Interpretation> tableTruth;
    @FXML
    private TextArea txtLog;
    @FXML
    private TabPane tabPane;

    public Controller() {
        init();
    }

    private void init() {

    }

    private void refresh() {
        tableTruth.getColumns().clear();
        txtPosForm.setText("");
        txtLog.setText("");
        txtLog.setWrapText(true);

        labelResult.setTextFill(Color.web("000"));
        labelResult.setText("Result: ???");
    }

    private void updateTruthTable(IExpression selected) {
        if (selected == null)
            return;

        TruthTable tmp = TruthTable.generate(selected);

        tableTruth.getColumns().clear();

        for (Variable i : tmp.get(0)) {
            String colName = i.name + "";
            if (colName.equals(" "))
                colName = "result";

            TableColumn<Interpretation, Integer> varCol = new TableColumn<>(colName);
            varCol.setCellValueFactory(new VarListValueFactory());
            tableTruth.getColumns().add(varCol);
        }

        ObservableList<Interpretation> tableData = FXCollections.observableList(tmp);
        tableTruth.setItems(tableData);
    }

    @FXML
    public void addNewPremise() {
        add(txtNewPremise, listPremise);
    }

    private void add(TextField txtField, ListView<IExpression> list) {
        String str = txtField.getText();

        refresh();

        try {
            IExpression expr = RecursiveDescentParser.parse(str);
            list.getItems().add(expr);
            txtField.clear();
        } catch (Exception ex) {
            txtLog.setText("Wrong format!");
        }
    }

    @FXML
    public void addNewProof() {
        add(txtNewProof, listProof);
    }

    @FXML
    public void selectPremise(MouseEvent event) {
        select(listPremise, event);
    }

    @FXML
    public void selectProof(MouseEvent event) {
        select(listProof, event);
    }

    private void select(ListView<IExpression> listView, MouseEvent event) {
        refresh();

        if (tabPane.getSelectionModel().getSelectedIndex() == 0)
            tabPane.getSelectionModel().select(1);

        MouseButton button = event.getButton();
        IExpression selected = listView.getSelectionModel().getSelectedItem();

        if (button == MouseButton.PRIMARY) {
            updateTruthTable(selected);
            generatePosForm(selected);
        } else if (button == MouseButton.SECONDARY) {
            listView.getItems().remove(selected);
        }
    }

    private void generatePosForm(IExpression selected) {
        if (selected == null)
            return;

        List<Term> maxterms = (new TermGeneration()).exec(selected);
        txtPosForm.setText(StringUtils.termsToString(maxterms));
    }

    @FXML
    public void calculate() {
        if (listPremise.getItems().size() == 0 || listProof.getItems().size() == 0) {
            txtLog.setText("You haven't enter any premises/proofs");
            return;
        }

        List<IExpression> premises = listPremise.getItems();
        List<IExpression> proofs = listProof.getItems();

        Robinson robinson = new Robinson();

        setLabelResult(robinson.exec(premises, proofs));
        txtLog.setText(robinson.getLog());
    }

    private void setLabelResult(boolean result) {
        Color color;

        if (!result)
            color = Color.web("ff0000");
        else
            color = Color.web("00ff00");

        labelResult.setTextFill(color);
        labelResult.setText("Result: " + Boolean.toString(result));
    }
//    @FXML
//    private ListView<String> listPremise;
//    @FXML
//    private ListView<String> listProof;
//    @FXML
//    private TextField txtNewPremise;
//    @FXML
//    private TextField txtNewProof;
//    @FXML
//    private Label labelResult;
//    @FXML
//    private Label txtPosForm;
//    @FXML
//    private TableView<Interpretation> tableTruth;
//    @FXML
//    private TextArea txtLog;
//    @FXML
//    private TabPane tabPane;
//
//    public Controller() {
//        init();
//    }
//
//    private void init() {
//
//    }
//
//    private void refresh() {
//        tableTruth.getColumns().clear();
//        txtPosForm.setText("");
//    }
//
//    private void updateTruthTable(String selected) {
//        if (selected == null)
//            return;
//
//        IExpression expr = RecursiveDescentParser.parse(selected);
//        TruthTable tmp = TruthTable.generate(expr);
//
//        tableTruth.getColumns().clear();
//
//        for (Variable i : tmp.get(0)) {
//            String colName = i.name + "";
//            if (colName.equals(" "))
//                colName = "result";
//
//            TableColumn<Interpretation, Integer> varCol = new TableColumn<>(colName);
//            varCol.setCellValueFactory(new VarListValueFactory());
//            tableTruth.getColumns().add(varCol);
//        }
//
//        ObservableList<Interpretation> tableData = FXCollections.observableList(tmp);
//        tableTruth.setItems(tableData);
//    }
//
//    @FXML
//    public void addNewPremise() {
//        add(txtNewPremise, listPremise);
//    }
//
//    private void add(TextField txtField, ListView<String> list) {
//        String expr = txtField.getText();
//
//        try {
//            RecursiveDescentParser.parse(expr);
//            list.getItems().add(expr);
//            txtField.clear();
//        } catch (Exception ex) {
////            ex.printStackTrace();
//            txtLog.setText("Wrong format!");
//        }
//    }
//
//    @FXML
//    public void addNewProof() {
//        add(txtNewProof, listProof);
//    }
//
//    @FXML
//    public void selectPremise(MouseEvent event) {
//        select(listPremise, event);
//    }
//
//    @FXML
//    public void selectProof(MouseEvent event) {
//        select(listProof, event);
//    }
//
//    private void select(ListView<String> listView, MouseEvent event) {
//        refresh();
//
//        if (tabPane.getSelectionModel().getSelectedIndex() == 0)
//            tabPane.getSelectionModel().select(1);
//
//        MouseButton button = event.getButton();
//        String selected = listView.getSelectionModel().getSelectedItem();
//
//        if (button == MouseButton.PRIMARY) {
//            updateTruthTable(selected);
//            generatePosForm(selected);
//        } else if (button == MouseButton.SECONDARY) {
//            listView.getItems().remove(selected);
//        }
//    }
//
//    private void generatePosForm(String selected) {
//        if (selected == null)
//            return;
//
//        IExpression expr = RecursiveDescentParser.parse(selected);
//        List<Term> maxterms = (new TermGeneration()).exec(expr);
//        txtPosForm.setText(StringUtils.termsToString(maxterms));
//    }
//
//    @FXML
//    public void calculate() {
//        if (listPremise.getItems().size() == 0 || listProof.getItems().size() == 0) {
//            txtLog.setText("You haven't enter any premises/proofs");
//            return;
//        }
//
//        List<IExpression> premises = new ArrayList<>();
//        List<IExpression> proofs = new ArrayList<>();
//
//        for (String str : listPremise.getItems())
//            premises.add(RecursiveDescentParser.parse(str));
//
//        for (String str : listProof.getItems())
//            proofs.add(RecursiveDescentParser.parse(str));
//
//        Robinson robinson = new Robinson();
//        setLabelResult(robinson.exec(premises, proofs));
//
//        txtLog.setWrapText(true);
//        txtLog.setText(robinson.getLog());
//    }
//
//    private void setLabelResult(boolean result) {
//        Color color;
//
//        if (!result)
//            color = Color.web("ff0000");
//        else
//            color = Color.web("00ff00");
//
//        labelResult.setTextFill(color);
//        labelResult.setText("Result: " + Boolean.toString(result));
//    }
//}
}
