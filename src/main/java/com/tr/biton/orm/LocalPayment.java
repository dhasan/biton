package com.tr.biton.orm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("localtype")
@Table(name="localpayments")
public class LocalPayment extends Payment {

}
