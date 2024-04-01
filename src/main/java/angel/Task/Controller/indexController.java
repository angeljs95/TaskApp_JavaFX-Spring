package angel.Task.Controller;

import angel.Task.Entity.Task;
import angel.Task.Services.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class indexController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(indexController.class);
    @Autowired
    private TaskService taskService;

    @FXML
    private TableView<Task> table_task;
    @FXML
    private TableColumn<Task, Integer> id_column;
    @FXML
    private TableColumn<Task, String> task_column;
    @FXML
    private TableColumn<Task, String> manager_column;
    @FXML
    private TableColumn<Task, String> status_column;

    //this metodo estara a la excucha de cambios en la tabla para asi aggregarlos automaticament
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();
    @FXML
    private TextField txt_task;
    @FXML
    private TextField txt_manager;
    @FXML
    private TextField txt_status;
    private Integer idTask;
    @FXML
    private Button btn_add;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_task.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configureColumn();
        listTask();
    }

    private void configureColumn() {
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        task_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        manager_column.setCellValueFactory(new PropertyValueFactory<>("manager"));
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void listTask() {

        logger.info("++++++++++++++Ejecutando listado de tareas++++++++++++++");
        //limpiamos la tabla
        taskList.clear();
        //Relacionamos tasklist que es de tipoobservable y lo conectamos a la base de datos
        taskList.addAll(taskService.listTask());
        //seteamos los valores de la base de datos en la tabla
        table_task.setItems(taskList);
    }

    public void addTask() {
        if (txt_task.getText().isEmpty()) {
            showMesagge("Error", "Task name is required");
            txt_task.requestFocus();
            return;
        } else {
            Task task = new Task();
            getFormData(task);
            task.setId(null);
            taskService.saveTask(task);
            showMesagge("Information", "the task was added successfully");
            cleanfields();
            listTask();
        }
    }

    public void updateTask() {

        if (idTask == null) {
            showMesagge("Error", "Select a task to continue");
            return;
        } else if (txt_task.getText().isEmpty()) {
            showMesagge("Error", "Task name is required");
            txt_task.requestFocus();
            return;
        } else {
            var task = new Task();
            getFormData(task);
            taskService.saveTask(task);
            showMesagge("Information","the task was updated successfully");
            cleanfields();
            listTask();
        }
    }

    public void deleteTask(){
        if(idTask== null){
            showMesagge("Error", "Select a task to delete");
        }else{
            Task task= new Task(idTask,null,null,null);
            taskService.removeTask(task);
            showMesagge("Information", "Task was removed successflluy");
            cleanfields();
            listTask();
      }
    }

    private void showMesagge(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void getFormData(Task task) {
        if (idTask != null) {
            task.setId(idTask);
        }
        task.setName(txt_task.getText());
        task.setManager(txt_manager.getText());
        task.setStatus(txt_status.getText());
    }

    public void getTaskTable() {
        Task task = table_task.getSelectionModel().getSelectedItem();
        if (task != null) {
            idTask = task.getId();
            txt_task.setText(task.getName());
            txt_manager.setText(task.getManager());
            txt_status.setText(task.getStatus());
        }
btn_add.setDisable(true);


    }

    public void cleanfields() {
        idTask= null;
        txt_task.clear();
        txt_manager.clear();
        txt_status.clear();
        btn_add.setDisable(false);
    }
}
