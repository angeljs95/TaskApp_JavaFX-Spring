package angel.Task.View;

import angel.Task.TaskApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SystemTaskFx extends Application {

    //    public static void main(String[] args) {
//        launch(args);
//    }
    //inicializamos la fabrica de spring
    private ConfigurableApplicationContext applicationContext;

    // cuando llamamos a ejecutar this clase desde el run de spring inicializamos la libreria
    // Spring con el init
    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(TaskApplication.class)
                .run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
