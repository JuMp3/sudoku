package it.jump3.sudoku;

import it.jump3.sudoku.control.SalesTaxesCalculator;
import it.jump3.sudoku.model.Item;
import it.jump3.sudoku.model.ItemType;
import it.jump3.sudoku.model.OutputItem;
import it.jump3.sudoku.view.ViewUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalesTaxesTests {

	@Autowired
	private SalesTaxesCalculator salesTaxesCalculator;

	@Autowired
	private ViewUtil viewUtil;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testA(){

		List<Item> itemList = new ArrayList<>();

		Item book = new Item();
		book.setQuantity(1);
		book.setName("book");
		book.setItemType(ItemType.BOOK);
		book.setPrice(12.49);
		itemList.add(book);

		Item musicCd = new Item();
		musicCd.setQuantity(1);
		musicCd.setName("music CD");
		musicCd.setItemType(ItemType.GOOD);
		musicCd.setPrice(14.99);
		itemList.add(musicCd);

		Item chocolateBar = new Item();
		chocolateBar.setQuantity(1);
		chocolateBar.setName("chocolate bar");
		chocolateBar.setItemType(ItemType.FOOD);
		chocolateBar.setPrice(0.85);
		itemList.add(chocolateBar);

		System.out.println("Output 1:");
		printResult(itemList);
	}

	@Test
	public void testB(){

		List<Item> itemList = new ArrayList<>();

		Item boxChocolate = new Item();
		boxChocolate.setQuantity(1);
		boxChocolate.setImported(true);
		boxChocolate.setName("box of chocolates");
		boxChocolate.setItemType(ItemType.FOOD);
		boxChocolate.setPrice(10.00);
		itemList.add(boxChocolate);

		Item perfume = new Item();
		perfume.setQuantity(1);
		perfume.setImported(true);
		perfume.setName("bottle of perfume");
		perfume.setItemType(ItemType.GOOD);
		perfume.setPrice(47.50);
		itemList.add(perfume);

		System.out.println("Output 2:");
		printResult(itemList);
	}

	@Test
	public void testC(){

		List<Item> itemList = new ArrayList<>();

		Item importedPerfume = new Item();
		importedPerfume.setQuantity(1);
		importedPerfume.setImported(true);
		importedPerfume.setName("bottle of perfume");
		importedPerfume.setItemType(ItemType.GOOD);
		importedPerfume.setPrice(27.99);
		itemList.add(importedPerfume);

		Item perfume = new Item();
		perfume.setQuantity(1);
		perfume.setName("bottle of perfume");
		perfume.setItemType(ItemType.GOOD);
		perfume.setPrice(18.99);
		itemList.add(perfume);

		Item headachePills = new Item();
		headachePills.setQuantity(1);
		headachePills.setName("headache pills");
		headachePills.setItemType(ItemType.MEDICAL_PRODUCT);
		headachePills.setPrice(9.75);
		itemList.add(headachePills);

		Item chocolates = new Item();
		chocolates.setQuantity(1);
		chocolates.setImported(true);
		chocolates.setName("box of chocolates");
		chocolates.setItemType(ItemType.FOOD);
		chocolates.setPrice(11.25);
		itemList.add(chocolates);

		System.out.println("Output 3:");
		printResult(itemList);
	}

	private void printResult(List<Item> itemList) {
		OutputItem outputItem = salesTaxesCalculator.computesItemList(itemList);
		System.out.println(viewUtil.getReceiptAsString(outputItem));
	}

	@Test
	public void testAll(){
		testA();
		System.out.println();
		testB();
		System.out.println();
		testC();
	}
}
