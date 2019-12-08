package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Label labelTimer;

    private Timer timer;

    private int timerSec;
    private int timerMin;
    private int timerHour;

    private String seconds;
    private String minutes;
    private String hours;

    public GameTimer(Label labelTimer) {
        timerSec = 0;
        timerMin = 0;
        timerHour = 0;
        this.labelTimer = new Label();
        this.labelTimer = labelTimer;
    }

    public String outputInfo() {
        return hours + ":" + minutes + ":" + seconds;
    }

    public void start() {
        Task task = new Task<Void>() {
            @Override public Void call() {
                final int max = 1000000;
                for (int i=1; i<=max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());

        new Thread(task).start();
    }

    public void startTime(){
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {

                    timerSec++;

                    Platform.runLater(new Runnable(){
                        public void run() {

                            if (timerSec == 60)
                            {
                                timerSec = 0;
                                timerMin++;
                            }
                            if (timerMin == 60)
                            {
                                timerMin = 0;
                                timerHour++;
                            }

                            seconds = Integer.toString(timerSec);
                            minutes = Integer.toString(timerMin);
                            hours = Integer.toString(timerHour);

                            if (timerSec <= 9)
                            {
                                seconds = "0" + timerSec;
                            }
                            if (timerMin <= 9)
                            {
                                minutes = "0" + timerMin;
                            }
                            if (timerHour <= 9)
                            {
                                hours = "0" + timerHour;
                            }

                            labelTimer.setText(" " + hours + ":" + minutes + ":" + seconds);
                        }

                    });

                }

            };
            timer.schedule(timerTask, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public void zeroing() {
        timerSec = 0;
        timerMin = 0;
        timerHour = 0;
    }
}
