package com.partyfinder.customInterface;

import java.util.ArrayList;

import com.partyfinder.model.TicketDetailsItem;

public interface PurchaseTicketsCallBack {
void selectedTicket(ArrayList<TicketDetailsItem> selectedItemList);
}
