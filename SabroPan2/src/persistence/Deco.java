package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import models.Expenditure;
import models.Product;
import models.User;

public class Deco {

	private static final String USERS_FILE_PATH = "./files/usersData.json";
	private static final String PRODUCTS_FILE_PATH = "./files/productsData.json";
	private static final String SELLS_FILE_PATH = "./files/sellsData.json";
	private static final String EXPENDITURES_FILE_PATH = "./files/expendituresData.json";
	private static final String FILE_DIRECTORY = "./files";

	public Deco() {
		checkFilesFolderExists();
		checkEssentialFilesExists();
	}

	public void checkEssentialFilesExists() {
		String[] URLVector = { PRODUCTS_FILE_PATH, USERS_FILE_PATH, SELLS_FILE_PATH, EXPENDITURES_FILE_PATH };
		for (int i = 0; i < URLVector.length; i++) {
			File file = new File(URLVector[i]);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void checkFilesFolderExists() {
		File file = new File(FILE_DIRECTORY);
		if (!file.exists() || file.length() == 0) {
			file = new File(FILE_DIRECTORY);
			file.mkdir();
		}
	}

	public String encodeProductsList(CopyOnWriteArrayList<Product> productsList) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonList = gson.toJson(productsList);
		return jsonList;
	}

	public String encodeUsersList(CopyOnWriteArrayList<User> usersList) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonList = gson.toJson(usersList);
		return jsonList;
	}
	
	public String encodeExpendituresList(CopyOnWriteArrayList<Expenditure> expendituresList) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonList = gson.toJson(expendituresList);
		return jsonList;
	}

	public CopyOnWriteArrayList<User> loadUsersData() {
		CopyOnWriteArrayList<User> usersList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(USERS_FILE_PATH));
			JsonElement jsonElement = jsonParser.parse(br);
			Type type = new TypeToken<CopyOnWriteArrayList<User>>() {
			}.getType();
			usersList = gson.fromJson(jsonElement, type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	public CopyOnWriteArrayList<Product> loadProductsData() {
		CopyOnWriteArrayList<Product> productsList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH));
			JsonElement jsonElement = jsonParser.parse(br);
			Type type = new TypeToken<CopyOnWriteArrayList<Product>>() {
			}.getType();
			productsList = gson.fromJson(jsonElement, type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return productsList;
	}

	public CopyOnWriteArrayList<Expenditure> loadExpendituresData() {
		CopyOnWriteArrayList<Expenditure> productsList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(EXPENDITURES_FILE_PATH));
			JsonElement jsonElement = jsonParser.parse(br);
			Type type = new TypeToken<CopyOnWriteArrayList<Expenditure>>() {
			}.getType();
			productsList = gson.fromJson(jsonElement, type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return productsList;
	}

	public void writeInFile(String toWriteString, String filePath) {
		String fileURL = "";
		switch (filePath) {
		case "1":
			fileURL = PRODUCTS_FILE_PATH;
			break;
		case "2":
			fileURL = USERS_FILE_PATH;
			break;
		case "3":
			fileURL = SELLS_FILE_PATH;
			break;
		case "4":
			fileURL = EXPENDITURES_FILE_PATH;
			break;
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fileURL, false));
			writer.write(toWriteString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
