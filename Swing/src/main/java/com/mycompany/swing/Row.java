package com.mycompany.swing;


import java.io.IOException;

public class Row {
    private int _id=0;
    private String product;
    private String id;
    private String issue;
    private String company;
    private String state;
    private String zipCode;
    private String complaintID;

    public Row(int _id, String product, String issue, String company,
               String state, String zipCode, String complaintID) {
        this._id=_id;
        this.product = product;
        this.issue = issue;
        this.company = company;
        this.state = state;
        this.zipCode = zipCode;
        this.complaintID = complaintID;
    }


    public int _id() { return _id; }

    public String product() {
        return product;
    }

    public String issue() {
        return issue;
    }

    public String company() {
        return company;
    }

    public String state() {
        return state;
    }

    public String zipCode() {
        return zipCode;
    }

    public String complaintID() {
        return complaintID;
    }

    @Override
    public String toString() {

        StopWordRemoval stopWordRemoval = new StopWordRemoval();
        product = stopWordRemoval.remoweStopWords(product);
        issue = stopWordRemoval.remoweStopWords(issue);
        company = stopWordRemoval.remoweStopWords(company);
        state = stopWordRemoval.remoweStopWords(state);
        zipCode = stopWordRemoval.remoweStopWords(zipCode);
        complaintID = stopWordRemoval.remoweStopWords(complaintID);
        product = RemovePunctuation.removePunc(product);
        issue = RemovePunctuation.removePunc(issue);
        company = RemovePunctuation.removePunc(company);
        state = RemovePunctuation.removePunc(state);
        zipCode = RemovePunctuation.removePunc(zipCode);
        complaintID = RemovePunctuation.removePunc(complaintID);


        return _id+"," + product + "," + issue
                + "," + company + "," + state
                + "," + zipCode + "," + complaintID;
    }
}
