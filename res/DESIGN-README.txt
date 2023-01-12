Changes from the previous version (Part 3 comparison with Part 2):

1) To improve the performance of the application, data from the API is being cached to avoid unnecessary repeated calls, This has
increased the overall speed of application in several features.

2) New interfaces has been created for Model, Controller and View to incorporate the new GUI feature. This is done so that the existing
interfaces are untouched thus not changing the client facing methods.

3) New class for controller GUI has been added leaving the old controller untouched. The main method has the option to invoke the either
the text bases controller or the gui based controller based on the user input.

4) We have used the General Command Callback design pattern while creating the GUI for our application. We have used this design as it eliminates the strong coupling between the controller
and the view. The controller need not know the properties of the view and it doesn't require to know about the Java AWT elements that are used in the View. The second advantage of using
this design is that, the control always comes to the controller when the user inputs any data and the View and model never interact with each other directly.
================================================================================================================================

Changes from the previous version (Part 2 comparison with Part 1):

1) Seperated out the logic of writing into a file:
                      Based on the feedback received from the Code Walk, we have decided to create a separate class that handles the file operation such as writing into a file.
            The write method is a generic method that can be used for any type of file. Related tests have been incorporated as well.

2) Used AlphaVantage API:
                In the previous version of the application, we queried a csv file inorder to get the data of stocks, but in the current version we have added support
           for the AlphaVantage API that helps us to fetch the data for larger range of dates. We have also added a configuration setting for the API that has to be used.
           If we need support for any other API in the future, we can simply change the configuration file and make some minimal changes in the code to support that API.

Changes in code:

1. The earlier hashmap used to pass data from controller to model was of the format {A={2022-01-01=25}} which has changed to
{A={2021-01-01=350,25}} where a new value has been appended in the internal hashmap that denotes the commission value. This change has
been made keeping the changes to minimum where the datatype change only incurs the method return and parameter type change.

2. Datevalidation has been moved to a different class to handle all date handling and exceptions, this is made to remove duplicacy in the
code and thus using one single class can prevent errors and be resilient.

3. Custom JSON parsers have been removed and been adapted with third party libraries to handle json parsing (json-simple), this makes the
code even more resilient as we are reusing the code from a licensed source.

For implementation of this project, we have used the Model-View-Controller (MVC) architecture as our design. The purpose of each is briefly explained below.

Model: The backend that contains all the data logic.
View: The frontend or graphical user interface (GUI) or (CLI).
Controller: The brains of the application that controls how data is displayed.

The advantage of using an MVC architecture is that it allows us to break our code into smaller modules logically. There is a separate module created for Model, View and the Controller.
By doing this the maintenance of the code becomes much easier and if there are any changes to be done in a particular module, those can be done without updating the other modules.

Our design contains three packages, they are:
1) controllers
2) models
3) views

1) Controllers:
              The main purpose of controller is to make method calls to the model based on the user input and then pass the result to view so that view can display the output to the user accordingly.
   The "controllers" package contains an Interface named "StockController" and a class named "Controller" which implements the StockController interface.

    -- StockController Interface:
                    This interface has a method named goController(), for which a method call is made in the "main" method.
    -- Controller Class:
                    This class implements goController() method of the StockController Interface.
    -- API:
           This interface has the methods that make the API calls to AlphaVantage.
    -- AlphaVantageClient:
                       This class implements the API interface. This class is used to implement the functionality of getting the value of the stock for a given date. This class has some methods which make calls to AlphaVantage API in order to get the data.
                  The API currently supports historic data of stocks upto 20 years.

2) Models:
             The purpose of model is to take input from the controller, process it accordingly by applying some logic and return the result to the controller.
    The "models" package contains an Interface name "StockModel", a class named Model which implements the StockModel interface and some helper classes which are used to Parse the JSON.

    -- StockModel Interface:
                   This interface has 21 methods in total, which are called by the Controller. All the 21 methods take in an input from the Controller, process the inputs, apply some logic to get the desired result and return the obtained result.
    -- Model Class:
                   This class implements all the methods of the StockModel interface, along with the 21 methods of the interface, there are some helper methods that are implemented in this class.
    -- DateValidation:
                    This class handles all the scenarios where validation of date is required.
    -- ToFileWriter:
                    This class is used to write content into a specific type of file. The logic is written in a different class as it will be much easier to add any new file types in future.
    -- MockModel:
                This class acts as a Mock model for the testing of Controller. It implements the StockModel Interface.
3) Views:
         The purpose of the view is to take input from the Controller and display some meaningful data on the screen such that the user can interpret the output easily.
       The "views" package contains an Interface named "StockViews", and a class named Views which implements the StockViews interface.

     -- StockView Interface:
                   This interface has 9 methods in total, which are called by the controller. These 9 methods take an input from the controller, process it and display some meaningful information on the screen.
     -- View Class:
                   This class implements the StockView Interface.

We have attached a detailed class diagram in the "res" folder for your reference.



