package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A class for classic date validation used across the application.
 */
public class Validations {

  /**
   * Check if the given string date is valid in yyyy-MM-dd format.
   *
   * @param date String of date.
   * @return boolean if valid/invalid.
   */
  public boolean validateDateFormat(String date) {
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate inputDate = LocalDate.parse(date, dtf);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  /**
   * check if the passed date object falls on a weekend.
   *
   * @param date Date object to be checked.
   * @return true/false if date falls on weekend.
   * @throws ParseException if any parse error.
   */
  public boolean checkIsWeekend(Date date) throws ParseException {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
    return dayOfTheWeek == 1 || dayOfTheWeek == 7;
  }

  /**
   * check if the passed date object falls in the future.
   *
   * @param date Date object to be checked.
   * @return true/false if date falls in the future.
   */
  boolean isDateFuture(Date date) {
    LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
    Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    return date.after(currentDate);
  }


  /**
   * check if the passed date object falls within 1 years in past.
   *
   * @param date Date object to be checked.
   * @return true/false if date falls within 1 years in past.
   * @throws ParseException if any parse error.
   */
  boolean checkIfGivenDateIsWithinRange(Date date) throws ParseException {
    LocalDate currentDate = LocalDate.now();
    Date twentyYears = Date.from(
        currentDate.minusYears(20).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return date.before(twentyYears);
  }

  /**
   * Helper method to throw appropriate error if the date is valid/invalid.
   *
   * @param date String date to be checked.
   * @return String with proper message.
   * @throws ParseException if parse error.
   */
  public String dateValidation(String date) throws ParseException {
    if (!validateDateFormat(date)) {
      return "\033[0;31m"
          + "Invalid Date Format provided, please provide a date in yyyy-MM-dd format";
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date enteredDate = sdf.parse(date);
      if (isDateFuture(enteredDate)) {
        return "\033[0;31m"
            + "Provided date is in future, please provide current date or past date";
      } else if (checkIfGivenDateIsWithinRange(enteredDate)) {
        return "\033[0;31m"
            + "Provided date is more than twenty years old, "
            + "please provide a date which is in the past twenty years";
      } else if (checkIsWeekend(enteredDate)) {
        return "\033[0;31m"
            + "Provided date falls on a weekend, please provide a date which is a weekday";
      } else {
        return "Valid";
      }
    }
  }

  /**
   * Returns the difference between dates in days.
   *
   * @param toDate   end date.
   * @param fromDate start date.
   * @return difference between dates
   * @throws ParseException if parse error.
   */
  public long checkIfToDateIsMoreThanFromDate(String toDate, String fromDate)
      throws ParseException {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Date firstDate = sdf.parse(toDate);
    Date secondDate = sdf.parse(fromDate);

    long diffInMillies = secondDate.getTime() - firstDate.getTime();
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    return diff;
  }

  /**
   * Method that validates if the given input type is valid or not.
   *
   * @param input the input value.
   * @return valid or invalid string.
   */
  public String validateInputType(String input) {
    try {
      int inputVal = Integer.parseInt(input);
      if (inputVal == 0) {
        return "Zero Stocks not allowed, Please enter stock greater than zero";
      } else if (inputVal < 0) {
        return
            "Negative Stocks not valid, please provide stock greater than zero";

      } else {
        return "Valid";
      }
    } catch (Exception e) {
      return "Invalid number of stocks provided, please provide a whole number";
    }
  }

  /**
   * Method that validates if the given commission is valid or not.
   *
   * @param input the commission.
   * @return valid or invalid string.
   */
  public String validateCommission(String input) {
    try {
      Float commissionValue = Float.parseFloat(input);
      if (commissionValue < 0) {
        return "Invalid commission provided, "
            + "please provide value greater than or equal to zero: ";
      } else {
        return "Valid";
      }
    } catch (NumberFormatException e) {
      return "Invalid commission provided, "
          + "please provide a valid value: ";
    }
  }

  /**
   * Method that validates if the given amount is valid or not.
   *
   * @param input the amount.
   * @return valid or invalid string.
   */
  public String validateAmount(String input) {
    try {
      Float amount = Float.parseFloat(input);
      if (amount <= 0) {
        return "Invalid amount provided, "
            + "please provide value greater than or equal to zero: ";
      } else {
        return "Valid";
      }
    } catch (NumberFormatException e) {
      return "Invalid amount provided, "
          + "please provide a valid value: ";
    }
  }

  /**
   * Method that validates if the given frequency is valid or not.
   *
   * @param input the frequency.
   * @return valid or invalid string.
   */
  public String validateFrequency(String input) {
    try {
      int frequency = Integer.parseInt(input);
      if (frequency <= 0) {
        return "Invalid frequency provided, "
            + "please provide value greater than or equal to zero: ";
      } else {
        return "Valid";
      }
    } catch (NumberFormatException e) {
      return "Invalid frequency provided, "
          + "please provide a valid value: ";
    }
  }
}
