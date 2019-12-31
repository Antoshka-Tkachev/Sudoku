package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
//        primaryStage.setTitle("Sudoku");
//        primaryStage.setResizable(false);
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();



//        FXMLLoader loader = new FXMLLoader(getClass().getResource("modeSurvivalWithTips.fxml"));
//        loader.setController(new ControllerModeSurvivalWithTips(primaryStage));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("modeClassic.fxml"));
//        loader.setController(new ControllerModeClassic(primaryStage));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("modeWithTips.fxml"));
//        loader.setController(new ControllerModeWithTips(primaryStage));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("modeSurvival.fxml"));
//        loader.setController(new ControllerModeSurvival(primaryStage));

//        DataBaseArrangement dbArrangement = new DataBaseArrangement();
//        dbArrangement.select();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("startMenu.fxml"));
        loader.setController(new ControllerStartMenu(primaryStage));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    @Override
    public void stop(){
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//переместить error в логику//
//Сделать невозможным изменение установленных элементов//
//Блокировка хода до выбора номера//
//изменить установленные элементы цветом//
//Написать подсказку//

//Найти и загрузить картинки из ресурсов//
//Написать жизни//
//Конец игры по ошибкам//
//Конец игры (победа) //
//Написать таймер (остановка таймера по концу игры)//
//Написать кнопку Пауза/плей с картинкой//
//Добавление зеленой подсветки по подстказке//

//блокировать кнопку как только наберется 9//
//Сохранение блокировки кнопок у которых 9 после паузы//
//Блокировка кнопок у которых 9 по подсказке//
//Добавить кнопку стирания поля//

//Выйти с уровня//
//Свое окно на победу//
//Свое окно на проажение, с разными конструкторами для "несобранной" и "ошибок"//
//Кнопку "новая игра" в окнах победы/поражения//
//Переделать методы из @FXML в setOnAction()//
//Создать общий абстрактный класс для всех контроллеров (структурировать приложение)//
//Разделить на режимы//

//Начальное меню с сложностью уровня, с выбором режима, магазин(Подсказки, фон)//
//Подключить базу данных на уровни
//Создать табличку в базе данных для каждого уровня сложности
//База данных для игрока
//Класс игорока
//Регистрация

//проверку на длину и уникальность никнейма
//регулярку на пароль
//хеш на пароль


//Свой профиль, отдельным окном
//Уровни с прогрессБаром и валютой за уровни

//Азартный режим игры со ставками
//Точно ли желаете закрыть программу?

