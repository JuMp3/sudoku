package it.jump3.sudoku.view;

import it.jump3.sudoku.control.SalesTaxesCalculator;
import it.jump3.sudoku.model.OutputItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Component
public class ViewUtil {

    @Autowired
    private SalesTaxesCalculator salesTaxesCalculator;

    private static final String SPACE = " ";
    private static final String PRICE_SEPARATOR = ": ";
    private static final String NEW_LINE = "\n";
    private static final String CURRENCY = "$";
    private static final String IMPORTED = "imported";

    public String getReceiptAsString(OutputItem outputItem) {

        if (!CollectionUtils.isEmpty(outputItem.getItems())) {

            StringBuilder sbOut = new StringBuilder();
            sbOut.append(outputItem.getItems().stream().map(item -> {
                StringBuilder sb = new StringBuilder();
                sb.append(item.getQuantity()).append(SPACE);
                if (item.isImported()) {
                    sb.append(IMPORTED).append(SPACE);
                }
                sb.append(item.getName()).append(PRICE_SEPARATOR);
                sb.append(getPriceAsString(item.getPrice()));
                if (item.getQuantity() > 1) {
                    sb.append(" (");
                    sb.append(getPriceAsString(salesTaxesCalculator.divide(item.getPrice(), item.getQuantity())));
                    sb.append(" each)");
                }
                return sb.toString();
            }).collect(Collectors.joining(NEW_LINE)));
            sbOut.append(NEW_LINE);
            sbOut.append("Sales Taxes").append(PRICE_SEPARATOR);
            sbOut.append(getPriceAsString(outputItem.getSalesTaxesTot())).append(NEW_LINE);
            sbOut.append("Total").append(PRICE_SEPARATOR);
            sbOut.append(getPriceAsString(outputItem.getTotal()));
            return sbOut.toString();
        } else {
            return "Empty basket";
        }
    }

    public String getPriceAsString(Double price) {
        return String.format("%.2f", price) + CURRENCY;
    }
}
