SETUP INSTRUCTIONS
    

This file shows how to setup the project and all the necessary instructions and dependencies
to the run the project.
    

Installation
    You will need Java 11 to run the .jar file.
    

Running the JAR file

To run the jar file use the command below 

        For Windows (Use Git Bash for better experience) 
        java -jar C://path//to//your//jar//file//Assignment-7.jar  

        For MacOS (In Terminal) 
        java -jar /path/to/your/jar/file/Assignment-7.jar


Running the program through IntelliJ 

Please follow the below steps to install the third party libraries used for this program. Mark the res folder as the Resources Root.
    
    

Usage of third party libraries.

This project makes use of three third party libraries which are used
        
•  to handle json file parsing which can be found here JSON-SIMPLE
                
•  to make charts which can be found here JFREECHART and,
                
•  dependency for jfreechart which can be found here JCOMMON
        
    

    Json_Simple library is distributed under the APACHE LICENCE
            2.0 which states that
        2. Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby
            grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license
            to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute
            the Work and such Derivative Works in Source or Object form.
    

    JCommon and JFreeChart library is distributed under the GNU LICENSE Software under the GPL may be run for all
        purposes, including commercial purposes and even as a tool for creating proprietary software, such as when using
        GPL-licensed compilers.[50] Users or companies who distribute GPL-licensed works (e.g. software), may charge a
        fee for copies or give them free of charge
    
    

Steps to install json-simple on IntelliJ

    1.Go to File -> Project Structure.
    
    2.On the left hand side click Libraries and click "From Maven" under the + button.
    
    3.Under donwload library search for "com.googlecode.json-simple:json-simple:1.1.1" and click ok.
    
    4.click ok under choose modules, make sure it is being added to your project.
    
    5.Click Apply and then OK.
    
    6.Similiary add the other two third party libraries using the below in search box.
        
        - org.jfree:jcommon:1.0.24 
        - org.jfree:jfreechart:1.5.3
    
    

List of libraries the application is using.

    This project is making use of three third party libraries json-simple, jcommon, jfreechart
    It uses other inbuilt JDK libraries like DateTimeFormatter, SimpleDateFormat, HttpClient etc which doesn't
        require any separate installation.


PARTS COMPLETED FOR THE ASSIGNMENT - 7:

• The Portfolio rebalancing feature is added to the program.
• The Portfolio rebalancing feature is exposed through the Text based interface.
• The Portfolio rebalancing feature is exposed through the GUI.   


    
How to use the program for Portfolio Rebalancing (Text Based Interface)

Now that your jar file is running follow the below steps as an example.

• Select 1 for Text based interface.
• Create a smart portfolio in order to proceed to rebalance.   
    
Reabalance a portfolio on a particular date 2022-10-28

• In the main menu Press 10 to get cost basis of a portfolio  
• Enter the portfolio id (1 in this case)    
• Enter the date in yyyy-MM-DD format on for the portfolio rebalancing. (2022-10-28 in this case)    
• Enter the number of stocks to be rebalanced (i.e the number of stocks in portfolio involved in rebalancing, in this case we are rebalancing all the 2 stocks present in the portfolio)   
• Enter the ticker symbol for the stock 1.
• Enter the new weight for the stock 1.
• Enter the ticker symbol for the stock 2.
• Enter the new weight for the stock 2.
• The portfolio will be rebalanced and the status will be printed on the screen.
• Press (n) to get back to the main menu. 



How to use the program for Portfolio Rebalancing (Graphical User Interface)

Now that your jar file is running follow the below steps as an example.

• Select 2 for Graphical User interface.
• Create a smart portfolio in order to proceed to rebalance.    
    
Reabalance a portfolio on a particular date 2022-10-28

• In the main menu click on the Portfolio Rebalancing option. 
• Enter or select the portfolio id from the drop down menu. (1 in this case)    
• Enter the date in yyyy-MM-DD format on for the portfolio rebalancing. (2022-10-28 in this case)    
• Click on the Add Stock Weights button to proceed assign new weights to the stocks. (In the next step, all the 2 stocks available in portfolio will be   assigned new weights)
• Enter the ticker symbol for the stock 1.
• Enter the new weight for the stock 1.
• Click on Add button to add the stock and its new weight to the table.
• Enter the ticker symbol for the stock 2.
• Enter the new weight for the stock 2.
• Click on Add button to add the stock and its new weight to the table.
• After finishing entering the stock ticker symbols along with their new weightss,Click on the Reabalance and create to assign the stock its new weight.
• Click on create to finish rebalancing the portfolio.
• The portfolio will be rebalanced and the status will be printed on the screen. Click on OK.
• Click on Back to main Menu button to get back to the main menu. 

   

Exiting the program
    
• Press on the close button on the top right corner with symbol 'X' to exit the program.



Code Changes

Model:

• Added 3 new methods to the model to get portfolio rebalancing.
• There is one main method rebalancePort() that performs the buy and sell 
    transactions needed to rebalance the portfolio and uses helper methods
    to define what those transactions should be.
• There is a helper method 'rebalancePortMap()' which collects all the data in 
    for the stocks included in rebalancing the portfolio (shares, price, 
    total value of all stocks in rebalancing). It also makes sure that all
    stocks being balanced are currently in the portfolio
• The helper method translateBalanceMap then takes the values provided by the 
    rebalancePortMap() method and the user defined weights to create a map
    of stock symbol strings to a double array which indicates whether to buy
    or sell stock and how much to transact.
• Once the helper method translate the map, the rebalancePort() method completes
    all necessary transactions and returns a status message.


View:
• The implemenation of the Features interface, GuiViewImpl was changed.
• A method, rebalance() was added which takes in all user inputs and converts them 
    to inputs necessary for the model. It passes all these inputs to the model
    an performs necessary transactions.

    

    

    

