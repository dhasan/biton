package com.tr.biton.orm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("PCHANNELTYPE")
@Table(name="PAYMENTCHANNEL")
public class PaymentChannelPayment extends Payment{

	
}
