package tungtung;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/** Provides a JavaFX chat interface for the Tung Tung chatbot. */
public class TungTungGui extends Application {
    private final ChatbotEngine engine = new ChatbotEngine();
    private final VBox messages = new VBox(10);
    private Stage stage;
    private final Image botAvatar = new Image(
            getClass().getResourceAsStream("/images/tungtung-avatar.png"));
    private final Image userAvatar = new Image(
            getClass().getResourceAsStream("/images/user-avatar.png"));

    /**
     * Builds and displays the chatbot window.
     *
     * @param stage primary JavaFX window.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        messages.setPadding(new Insets(16));
        messages.setStyle("-fx-background-color: #f1fff4;");
        addBotMessage("Hello! Tung Tung Sahere!\nHow can I assist?");

        ScrollPane history = new ScrollPane(messages);
        history.setFitToWidth(true);
        history.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        TextField input = new TextField();
        input.setPromptText("Type a command...");
        Button send = new Button("Send");
        send.setDefaultButton(true);
        Runnable submit = () -> submit(input);
        send.setOnAction(event -> submit.run());
        input.setOnAction(event -> submit.run());

        HBox composer = new HBox(8, input, send);
        composer.setPadding(new Insets(10));
        composer.setAlignment(Pos.CENTER);
        HBox.setHgrow(input, javafx.scene.layout.Priority.ALWAYS);

        VBox root = new VBox(history, composer);
        VBox.setVgrow(history, javafx.scene.layout.Priority.ALWAYS);
        Scene scene = new Scene(root, 520, 640);
        stage.setTitle("Tung Tung");
        stage.setScene(scene);
        stage.show();
    }

    private void submit(TextField input) {
        String command = input.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        addUserMessage(command);
        if (command.equals("bye")) {
            addBotMessage("Bye! Tung Tung Sagone!");
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(event -> stage.close());
            pause.play();
            input.clear();
            return;
        }
        addBotMessage(engine.execute(command));
        input.clear();
    }

    private void addUserMessage(String text) {
        addMessage(text, "#00c9c9", Pos.CENTER_RIGHT, userAvatar);
    }

    private void addBotMessage(String text) {
        addMessage(text, "#b8f5c1", Pos.CENTER_LEFT, botAvatar);
    }

    private void addMessage(String text, String color, Pos alignment, Image avatar) {
        Label message = new Label(text);
        message.setWrapText(true);
        message.setMaxWidth(420);
        message.setPadding(new Insets(10));
        message.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 12;");
        ImageView avatarView = new ImageView(avatar);
        avatarView.setFitWidth(42);
        avatarView.setFitHeight(42);
        HBox row = alignment == Pos.CENTER_RIGHT
                ? new HBox(8, message, avatarView)
                : new HBox(8, avatarView, message);
        row.setAlignment(alignment);
        messages.getChildren().add(row);
    }
}
