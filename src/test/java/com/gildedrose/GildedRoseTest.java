package com.gildedrose;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GildedRoseTest {
	private static final String AGE_CHEESE = "Aged Brie";
	private static final String CONCERT_TICKET = "Backstage passes to a TAFKAL80ETC concert";
	private static final String LEGEND_ITEM = "Sulfuras, Hand of Ragnaros";
	private static final String NORMAL_ITEM = "naname";
	// 일반 아이템 하나 quality 양수, sellin 양수

	private static final int ANY_PLUS_NUM = 40;
	private static final int LEGEND_PRICE_NUM = 80;
	private static final int MINUS_NUM = -1;

	private static final int BIGGEST_PRICE = 50;
	private static final int BIGGER_THEN_10 = 40;
	private static final int BETWEEN_10_TO_6 = 8;
	private static final int BETWEEN_5_TO_1 = 3;

	@Test
	public void testNormal(){
		Item[] items = new Item[]{
				new Item(NORMAL_ITEM, ANY_PLUS_NUM, ANY_PLUS_NUM),
				new Item(NORMAL_ITEM, ANY_PLUS_NUM, 0),
				new Item(NORMAL_ITEM, 0, ANY_PLUS_NUM),
				new Item(NORMAL_ITEM, MINUS_NUM, 0),
		};

		GildedRose gildedRose = new GildedRose(items);

		gildedRose.updateQuality();

		assertEquals(4, items.length);
		assertEquals(ANY_PLUS_NUM -1, items[0].sellIn);
		assertEquals(ANY_PLUS_NUM -1, items[0].quality);

		assertEquals(ANY_PLUS_NUM -1, items[1].sellIn);
		assertEquals(0, items[1].quality);

		assertEquals(-1, items[2].sellIn);
		assertEquals(ANY_PLUS_NUM -2, items[2].quality);

		assertEquals(MINUS_NUM-1, items[3].sellIn);
		assertEquals(0, items[3].quality);
	}

	@Test
	public void testLegend(){
		Item[] items = new Item[]{
				new Item(LEGEND_ITEM, ANY_PLUS_NUM, LEGEND_PRICE_NUM),
				new Item(LEGEND_ITEM, -1, LEGEND_PRICE_NUM)
		};
		GildedRose gildedRose = new GildedRose(items);

		gildedRose.updateQuality();

		assertEquals(2, items.length);
		assertEquals(ANY_PLUS_NUM, items[0].sellIn);
		assertEquals(LEGEND_PRICE_NUM, items[0].quality);

		assertEquals(-1, items[1].sellIn);
		assertEquals(LEGEND_PRICE_NUM, items[1].quality);
	}

	@Test
	public void testAgeCheese(){
		Item[] items = new Item[]{
				new Item(AGE_CHEESE, ANY_PLUS_NUM, ANY_PLUS_NUM),
				new Item(AGE_CHEESE, 0, ANY_PLUS_NUM),
				new Item(AGE_CHEESE, ANY_PLUS_NUM, BIGGEST_PRICE),
				new Item(AGE_CHEESE, 0, BIGGEST_PRICE),
		};
		GildedRose gildedRose = new GildedRose(items);

		gildedRose.updateQuality();

		assertEquals(4, items.length);
		assertEquals(ANY_PLUS_NUM -1, items[0].sellIn);
		assertEquals(ANY_PLUS_NUM +1, items[0].quality);

		assertEquals(-1, items[1].sellIn);
		assertEquals(ANY_PLUS_NUM +2, items[1].quality);

		assertEquals(ANY_PLUS_NUM -1, items[2].sellIn);
		assertEquals(BIGGEST_PRICE, items[2].quality);

		assertEquals(-1, items[3].sellIn);
		assertEquals(BIGGEST_PRICE, items[3].quality);
	}

	@Test
	public void testConcert(){
		Item[] items = new Item[]{
				new Item(CONCERT_TICKET, BIGGER_THEN_10, ANY_PLUS_NUM),
				new Item(CONCERT_TICKET, BETWEEN_10_TO_6, ANY_PLUS_NUM),
				new Item(CONCERT_TICKET, BETWEEN_5_TO_1, ANY_PLUS_NUM),
				new Item(CONCERT_TICKET, 0, ANY_PLUS_NUM),
				new Item(CONCERT_TICKET, BIGGER_THEN_10, BIGGEST_PRICE),
				new Item(CONCERT_TICKET, BETWEEN_10_TO_6, BIGGEST_PRICE),
				new Item(CONCERT_TICKET, BETWEEN_5_TO_1, BIGGEST_PRICE),
				new Item(CONCERT_TICKET, BETWEEN_5_TO_1, BIGGEST_PRICE-1),
				new Item(CONCERT_TICKET, BETWEEN_5_TO_1, BIGGEST_PRICE-2),
		};
		GildedRose gildedRose = new GildedRose(items);

		gildedRose.updateQuality();

		assertEquals(9, items.length);
		assertEquals(BIGGER_THEN_10 -1, items[0].sellIn);
		assertEquals(ANY_PLUS_NUM +1, items[0].quality);

		assertEquals(BETWEEN_10_TO_6 -1, items[1].sellIn);
		assertEquals(ANY_PLUS_NUM +2, items[1].quality);

		assertEquals(BETWEEN_5_TO_1 -1, items[2].sellIn);
		assertEquals(ANY_PLUS_NUM +3, items[2].quality);

		assertEquals(-1, items[3].sellIn);
		assertEquals(0, items[3].quality);

		assertEquals(BIGGER_THEN_10 -1, items[4].sellIn);
		assertEquals(BIGGEST_PRICE, items[4].quality);

		assertEquals(BETWEEN_10_TO_6 -1, items[5].sellIn);
		assertEquals(BIGGEST_PRICE, items[5].quality);

		assertEquals(BETWEEN_5_TO_1 -1, items[6].sellIn);
		assertEquals(BIGGEST_PRICE, items[6].quality);

		assertEquals(BETWEEN_5_TO_1 -1, items[7].sellIn);
		assertEquals(BIGGEST_PRICE, items[7].quality);

		assertEquals(BETWEEN_5_TO_1 -1, items[8].sellIn);
		assertEquals(BIGGEST_PRICE, items[8].quality);
	}


}
