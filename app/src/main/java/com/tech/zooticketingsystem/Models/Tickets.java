package com.tech.zooticketingsystem.Models;

public class Tickets {

    String id;
    String adultsTickets;
    String childTickets;
    String customerName;
    String date;
    String total;

    public Tickets() {
    }

    public Tickets(String id, String adultsTickets, String childTickets, String customerName, String date, String total) {
        this.id = id;
        this.adultsTickets = adultsTickets;
        this.childTickets = childTickets;
        this.customerName = customerName;
        this.date = date;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdultsTickets() {
        return adultsTickets;
    }

    public void setAdultsTickets(String adultsTickets) {
        this.adultsTickets = adultsTickets;
    }

    public String getChildTickets() {
        return childTickets;
    }

    public void setChildTickets(String childTickets) {
        this.childTickets = childTickets;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
