Features

Upon running the .jar file the user is prompted with an option to choose either a text based interface or GUI based interface to interact with the program with the following features:

1. Allow a user to create one or more portfolios with shares of one or more stock. It allows the user to create two types of portfolios i.e "Flexible Portfolio" and "Inflexible Portfolio".
Once created, shares cannot be added or removed from the Inflexible portfolio, but the user can modify the Flexible portfolio that they created.

This feature is implemented completely, where a user can create 1 or more portfolios with as many stocks as his choice (From a give set of stocks). 
Once a portfolio is created the CLI returns a portfolio id which user can use for later querying. The script handles cases such as invalid stock name.

2. Examine the composition of a portfolio.

This feature is implemented completely, whereupon entering a portfolio id, the user can see the composition of the portfolio i.e a list of stock names
and its respective quantities. The composition is displayed on the screen. The script handles cases such as invalid portfolio id.

3. Determine the total value of a portfolio on a certain date.

This feature is implemented completely, where when user gives a particular portfolio id and a date it returns the total price of his stocks and total
price of his portfolio on the screen. The script handles cases such as invalid portfolio id or date.

4. Persist a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files)

This feature is implemented completely, where a user can
- upload a portfolio in a json format and is returned with a portfolio id which he/she can use to query later. User has to mention the path of the file which
    he/she wishes to upload. Cases are handled such as file being invalid or not found.

- download a portfolio in a json format and is returned a path where the json file was downloaded. He/She has to input which portfolio id and the directory where he/she
    wishes to download the file. Cases are handled such as directory invalid or not found or invalid portfolio id.

5. Purchase and Sell Stocks

This feature is implemented completely, This feature allows the user to Purchase or Sell stocks that are available in their portfolio. The user can only buy/sell stocks using their Flexible portfolio
as the Inflexible portfolios are immutable, and they cannot be modified. All the error cases are handled such as stock not available, quantity of stock more than what is available etc.
The user needs to provide the portfolio ID and the information about the stocks that they want to either purchase or sell.

In the text based interface user is prompted to enter a commission fee
In the GUI interface it is optional for the user to enter a commission fee, if nothing is specified by default program assumes commission as 0.

6. Commission Fee

This feature is implemented completely, Commission Fee is associated with each and every transaction that a user make in the Stock market. When ever the user is creating a portfolio, uploading a portfolio, purchasing a stock
or selling a stock, he/she needs to provide the commission fee that they had to pay while performing that particular transaction.

7. Cost Basis

This feature is implemented completely, This feature allows the user to check how much amount have they invested in the Stock Market upto the date they provide as input. The user needs to provide portfolio ID and the date, our
application will calculate the amount of money that the user invested which includes the commission fee that he/she paid during that transaction.

8. Portfolio at a Glance

This feature is implemented completely, This feature helps the user to get an overview on how their portfolio is performing over time. The user needs to provide the portfolio ID, start date and end date and our application
will show a detailed graph on how the portfolio is performing over the time range that is provided.

The above features are available in both Text based and GUI interfaces, In addition to the above GUI offers three more features as below.

1. Start to finish - Dollar cost Averaging

This feature is implemented completely, It offers the user to create a portfolio with a certain amount, commission and a stock names and weights option to invest the amount
from a particular start date to end date (can be indefinite).

2. Non recurring Dollar cost Averaging to existing portfolio

This feature is implemented completely, It offers the user to invest a list of stocks (along with their ratios) for a particular amount to an existing flexible portfolio which will then be added
to his portfolio based on the given amount and the corresponding weights.

3. Performance of chart to summarise (Optional: for extra credit)

This feature is implemented completely, It offers the ability to draw a bar chart in the GUI to summarise the performance of a portfolio over a specific time frame.

4. Rebalancing of Portfolio

This feature is implemented completely. It offers the ability to rebalance any stocks in the portfolio to new propertions. These transactions will not be included in the cost basis.