package edu.bsu.cs222.twitterbot;

import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UIController extends Application {

	private String selectedUser;
	protected String apiKey;
	protected String apiSecret;
	protected ParseFromJSONFile usersParser;
	protected JSONObject usersJSONObject;
	protected OAuth oAuth;
	protected AlertFactory alertFactory = new AlertFactory();
	
	private LaunchUI launchUI;
	private ApiUI apiUI;
	private VerifyUI verifyUI;
	private TypeOfTweetUI tweetTypeUI;
	private ManualTweetUI manualUI;
	private AutoTweetUI autoUI;
	private ScheduleTweetUI scheduleUI;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		initUIClasses(primaryStage);
		setUpAllUIClasses();
		setStage(primaryStage);
	}
	
	private void initUIClasses(Stage primaryStage) {
		launchUI = new LaunchUI(primaryStage, this);
		apiUI = new ApiUI(primaryStage, this);
		verifyUI = new VerifyUI(primaryStage, this);
		tweetTypeUI = new TypeOfTweetUI(primaryStage, this);
		manualUI = new ManualTweetUI(primaryStage, this);
		autoUI = new AutoTweetUI(primaryStage, this);
		scheduleUI = new ScheduleTweetUI(primaryStage, this);
	}
	
	private void setUpAllUIClasses() {
		launchUI.setUp();
		apiUI.setUp();
		verifyUI.setUp();
		tweetTypeUI.setUp();
		manualUI.setUp();
		autoUI.setUp();
		scheduleUI.setUp();
	}
	
	private void setStage(Stage primaryStage) {
		Scene startScene = launchUI.getStartScene();
		primaryStage.setTitle("Twitter Bot");
		primaryStage.setScene(startScene);
		primaryStage.setMinHeight(300);
		primaryStage.setMinWidth(600);
		primaryStage.show();
	}

	protected void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
	}
	
	protected void switchSceneToStartScene(Stage primaryStage) {
		primaryStage.setScene(launchUI.getStartScene());
	}
	
	protected void switchSceneToStartSceneAfterSave(Stage primaryStage) {
		String userName = apiUI.userNameInputField.getText();
		launchUI.userSelector.getItems().add(userName);
		primaryStage.setScene(launchUI.getStartScene());
	}
	
	protected void switchSceneToApiScene(Stage primaryStage) {
		primaryStage.setScene(apiUI.getAPIScene());
	}
	
	protected void switchSceneToVerifyScene(Stage primaryStage) {
		primaryStage.setScene(verifyUI.getVerifyScene());
	}
	
	protected void switchSceneToTweetTypeScene(Stage primaryStage) {
		selectedUser = launchUI.userSelector.getValue();
		if (selectedUser.equals("None")){
			alertFactory.createInfoAlert("You must select a username to start tweeting.");
		} else {
			primaryStage.setScene(tweetTypeUI.getTweetTypeTweetScene());
			setUpTweetService();
		}
	}
	
	private void setUpTweetService() {
		getApiValuesFromFile();
		createOAuthInstance();
		oAuth.createOAuthService();
		JSONObject userObject = usersParser.parseOutObject( selectedUser, usersJSONObject);
		String tokenString = usersParser.parseOutObjectValue("tokenString", userObject);
		String tokenSecret = usersParser.parseOutObjectValue("tokenSecret", userObject);
		oAuth.createAccessTokenFromValues(tokenString, tokenSecret);
	}
	
	protected void getApiValuesFromFile() {
		ParseFromJSONFile apiValueFileReader = new ParseFromJSONFile("twitter-values/api-values.json");
		JSONObject apiFileObject = apiValueFileReader.tryTtoReadFromFile();
		String apiKeyFromFile = apiValueFileReader.parseOutObjectValue("apiKey", apiFileObject);
		String apiSecretFromFile = apiValueFileReader.parseOutObjectValue("apiSecret", apiFileObject);
		apiKey = apiKeyFromFile;
		apiSecret = apiSecretFromFile;
	}
	
	protected void createOAuthInstance() {
		oAuth = new OAuth(apiKey, apiSecret);
	}
	
	protected void switchSceneToTweetScene(Stage primaryStage) {
		primaryStage.setScene(manualUI.getManualTweetScene());
	}
	
	protected void switchSceneToAutomaticTweetScene(Stage primaryStage) {
		primaryStage.setScene(autoUI.getAutomaticTweetScene());
	}
	
	protected void switchSceneToScheduleTweetScene(Stage primaryStage) {
		primaryStage.setScene(scheduleUI.getScheduleTweetScene());
	}
	
	protected String getNewUserName() {
		return apiUI.userNameInputField.getText();
	}

}