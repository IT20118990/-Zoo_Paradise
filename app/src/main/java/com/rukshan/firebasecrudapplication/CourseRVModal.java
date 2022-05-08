package com.rukshan.firebasecrudapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModal implements Parcelable {
    // creating variables for our different fields.
    private String cardName;
    private String cardDescription;
    private String cardPrice;
    private String bestCardSuitedFor;
    private String cardImg;
    private String cardLink;
    private String cardId;


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    // creating an empty constructor.
    public CourseRVModal() {

    }

    protected CourseRVModal(Parcel in) {
        cardName = in.readString();
        cardId = in.readString();
        cardDescription = in.readString();
        cardPrice = in.readString();
        bestCardSuitedFor = in.readString();
        cardImg = in.readString();
        cardLink = in.readString();
    }

    public static final Creator<CourseRVModal> CREATOR = new Creator<CourseRVModal>() {
        @Override
        public CourseRVModal createFromParcel(Parcel in) {
            return new CourseRVModal(in);
        }

        @Override
        public CourseRVModal[] newArray(int size) {
            return new CourseRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getBestCardSuitedFor() {
        return bestCardSuitedFor;
    }

    public void setBestCardSuitedFor(String bestCardSuitedFor) {
        this.bestCardSuitedFor = bestCardSuitedFor;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getCardLink() {
        return cardLink;
    }

    public void setCardLink(String cardLink) {
        this.cardLink = cardLink;
    }


    public CourseRVModal(String cardId, String cardName, String cardDescription, String cardPrice, String bestCardSuitedFor, String cardImg, String cardLink) {
        this.cardName = cardName;
        this.cardId = cardId;
        this.cardDescription = cardDescription;
        this.cardPrice = cardPrice;
        this.bestCardSuitedFor = bestCardSuitedFor;
        this.cardImg = cardImg;
        this.cardLink = cardLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardName);
        dest.writeString(cardId);
        dest.writeString(cardDescription);
        dest.writeString(cardPrice);
        dest.writeString(bestCardSuitedFor);
        dest.writeString(cardImg);
        dest.writeString(cardLink);
    }
}
