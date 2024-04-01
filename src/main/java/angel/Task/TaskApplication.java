package angel.Task;

import angel.Task.View.SystemTaskFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApplication {


	public static void main(String[] args) {
		//SpringApplication.run(TaskApplication.class, args);
		Application.launch(SystemTaskFx.class, args);
	}

}
