package com.tr.biton.orm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("LOCALTYPE")
@Table(name="LOCALPAYMENT")
public class LocalPayment extends Payment {

}
