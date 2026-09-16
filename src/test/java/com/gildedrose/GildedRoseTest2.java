package com.gildedrose;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class GildedRoseTest2 {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS =
            "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS =
            "Sulfuras, Hand of Ragnaros";
    private static final String NORMAL_ITEM = "Normal Item";

    private static final int MAX_QUALITY = 50;
    private static final int NORMAL_QUALITY = 40;

    private static final int EXPIRED = 0;
    private static final int ALREADY_EXPIRED = -5;

    private static final int BACKSTAGE_10_DAYS = 10;
    private static final int BACKSTAGE_5_DAYS = 5;


    // =================================================
    // Normal Item - 3개
    // =================================================

    @Test
    void normalItem_qualityDecreasesByOneBeforeSellDate() {
        Item item = new Item(NORMAL_ITEM, 10, NORMAL_QUALITY);

        updateQuality(item);

        assertEquals(9, item.sellIn);
        assertEquals(39, item.quality);
    }

    @Test
    void normalItem_qualityDecreasesByTwoAfterSellDate() {
        Item item = new Item(NORMAL_ITEM, EXPIRED, NORMAL_QUALITY);

        updateQuality(item);

        assertEquals(-1, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void normalItem_qualityNeverGoesBelowZero() {
        Item item = new Item(NORMAL_ITEM, 10, 0);

        updateQuality(item);

        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_qualityNeverGoesBelowZeroInExpired(){
        Item item = new Item(NORMAL_ITEM, EXPIRED, 0);

        updateQuality(item);

        assertEquals(0, item.quality);
    }


    // =================================================
    // Sulfuras - 2개
    // =================================================

    @Test
    void sulfuras_neverChanges() {
        Item item = new Item(SULFURAS, 10, 80);

        updateQuality(item);

        assertEquals(10, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfuras_neverChangesInAlreadyExpired() {
        Item item = new Item(SULFURAS, ALREADY_EXPIRED, 80);

        updateQuality(item);

        assertEquals(ALREADY_EXPIRED, item.sellIn);
        assertEquals(80, item.quality);
    }


    // =================================================
    // Aged Brie - 3개
    // =================================================

    @Test
    void agedBrie_qualityIncreasesByOneBeforeSellDate() {
        Item item = new Item(AGED_BRIE, 10, NORMAL_QUALITY);

        updateQuality(item);

        assertEquals(9, item.sellIn);
        assertEquals(41, item.quality);
    }

    @Test
    void agedBrie_qualityIncreasesByTwoAfterSellDate() {
        Item item = new Item(AGED_BRIE, EXPIRED, NORMAL_QUALITY);

        updateQuality(item);

        assertEquals(-1, item.sellIn);
        assertEquals(42, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceedsFifty() {
        Item item = new Item(AGED_BRIE, 10, MAX_QUALITY);

        updateQuality(item);

        assertEquals(MAX_QUALITY, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceedsFiftyInExpired() {
        Item item = new Item(AGED_BRIE, EXPIRED, MAX_QUALITY);

        updateQuality(item);

        assertEquals(MAX_QUALITY, item.quality);
    }


    // =================================================
    // Backstage Pass - 5개
    // =================================================

    @Test
    void backstagePass_qualityIncreasesByOneWhenMoreThanTenDaysRemain() {
        Item item = new Item(
                BACKSTAGE_PASS,
                BACKSTAGE_10_DAYS + 1,
                NORMAL_QUALITY
        );

        updateQuality(item);

        assertEquals(41, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesByTwoWhenTenDaysRemain() {
        Item item = new Item(
                BACKSTAGE_PASS,
                BACKSTAGE_10_DAYS,
                NORMAL_QUALITY
        );

        updateQuality(item);

        assertEquals(42, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesByThreeWhenFiveDaysRemain() {
        Item item = new Item(
                BACKSTAGE_PASS,
                BACKSTAGE_5_DAYS,
                NORMAL_QUALITY
        );

        updateQuality(item);

        assertEquals(43, item.quality);
    }

    @Test
    void backstagePass_qualityBecomesZeroAfterConcert() {
        Item item = new Item(
                BACKSTAGE_PASS,
                EXPIRED,
                NORMAL_QUALITY
        );

        updateQuality(item);

        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void backstagePass_qualityNeverExceedsFifty() {
        Item item = new Item(
                BACKSTAGE_PASS,
                BACKSTAGE_5_DAYS,
                MAX_QUALITY - 1
        );

        updateQuality(item);

        assertEquals(MAX_QUALITY, item.quality);
    }


    // =================================================
    // Helper
    // =================================================

    private void updateQuality(Item item) {
        GildedRose gildedRose =
                new GildedRose(new Item[]{item});

        gildedRose.updateQuality();
    }
}