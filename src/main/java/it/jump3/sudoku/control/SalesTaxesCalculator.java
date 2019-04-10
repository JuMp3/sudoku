package it.jump3.sudoku.control;

import it.jump3.sudoku.model.Item;
import it.jump3.sudoku.model.ItemType;
import it.jump3.sudoku.model.OutputItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalesTaxesCalculator {

    private static final int PLACES = 2;

    public OutputItem computesItemList(List<Item> itemList) {

        OutputItem outputItem = new OutputItem();
        List<Item> itemListOut = new ArrayList<>();
        Double salesTaxesTot = (double) 0, total = (double) 0;

        if (!CollectionUtils.isEmpty(itemList)) {
            for (Item item : itemList) {

                Item itemOut = new Item();
                BeanUtils.copyProperties(item, itemOut);

                Integer percentage = getPercentage(item);
                if (percentage > 0) {
                    Double percentagePrice = round(calculatePercentage(item.getPrice(), percentage), true);
                    itemOut.setPrice(multiply(Double.sum(item.getPrice(), percentagePrice), item.getQuantity()));
                    salesTaxesTot += multiply(percentagePrice, item.getQuantity());
                } else {
                    itemOut.setPrice(multiply(item.getPrice(), item.getQuantity()));
                }

                total += itemOut.getPrice();

                itemListOut.add(itemOut);
            }
        }

        outputItem.setItems(itemListOut);
        outputItem.setSalesTaxesTot(salesTaxesTot);
        outputItem.setTotal(total);

        return outputItem;
    }

    private Integer getPercentage(Item item) {
        Integer percentage = 0;
        if (item.isImported()) {
            percentage += 5;
        }
        if (ItemType.isApplicableSalesTax(item.getItemType())) {
            percentage += 10;
        }
        return percentage;
    }

    public Double calculatePercentage(Double price, Integer percentage) {
        return price * percentage / 100;
    }

    public Double multiply(Double number, int nTime) {
        BigDecimal bd = new BigDecimal(Double.toString(number));
        BigDecimal nTimeBd = new BigDecimal(Integer.toString(nTime));
        return bd.multiply(nTimeBd).doubleValue();
    }

    public Double round(Double price, boolean round05) {
        return round(price, round05, PLACES);
    }

    public Double round(Double price, boolean round05, int places) {

        // to round off the input digits
        BigDecimal bd = new BigDecimal(Double.toString(price));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        Double round = bd.doubleValue();

        // rounded up to the nearest 0.05
        return round05 ? (Math.round((round + 0.02) * 20.0) / 20.0) : round;
    }

    public Double divide(Double number, Integer divide) {
        BigDecimal bd = new BigDecimal(Double.toString(number));
        BigDecimal bdDivide = new BigDecimal(Double.toString(divide));
        return bd.divide(bdDivide).doubleValue();
    }
}
