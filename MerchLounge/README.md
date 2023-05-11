## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


## Statuses of the order

6 constant status for order delivery - 
1) Order placed. //orderplaced to packed is by seller.
2) Packed. // packed to shipped also by delivery agent.
3) Shipped. // delivery agent updates to delivered.
4) Delivered. // delivered to returned will be by del agent.
5) Returned. // returned to seller.
6) Refunded.

## Database Schema
Kindly refer to the Schema diagram and the UML class diagram to understand the structure of the database.

## Instructions to run the code
1. Open the project in VSCode
2. In the Explorer tab (left hand side) of VSCode, go to Java Projects > Referenced Libraries
3. Add the mysql-connector.jar file in it (found in the lib folder of the project)
4. Open the DAO_factory.java and change the password according to your password in mysql 
5. Open the DAO_Driver.java and run the code 
