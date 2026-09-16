package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Gilded Rose")
class GildedRoseTest3 {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS =
            "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS =
            "Sulfuras, Hand of Ragnaros";
    private static final String NORMAL_ITEM = "naname";

    private static final int ANY_PLUS_NUM = 40;
    private static final int LEGEND_PRICE_NUM = 80;
    private static final int MINUS_NUM = -1;

    private static final int MAX_QUALITY = 50;
    private static final int BIGGER_THAN_10 = 40;
    private static final int BETWEEN_10_TO_6 = 8;
    private static final int BETWEEN_5_TO_1 = 3;


    @Nested
    @DisplayName("일반 아이템")
    class NormalItemTest {

        @Test
        @DisplayName("판매 기한 전에는 sellIn과 quality가 1씩 감소한다")
        void decreaseSellInAndQualityBeforeSellDate() {
            Item item = new Item(
                    NORMAL_ITEM,
                    ANY_PLUS_NUM,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(ANY_PLUS_NUM - 1, item.sellIn);
            assertEquals(ANY_PLUS_NUM - 1, item.quality);
        }

        @Test
        @DisplayName("quality는 0보다 작아지지 않는다")
        void qualityNeverGoesBelowZero() {
            Item item = new Item(
                    NORMAL_ITEM,
                    ANY_PLUS_NUM,
                    0
            );

            updateQuality(item);

            assertEquals(ANY_PLUS_NUM - 1, item.sellIn);
            assertEquals(0, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 지나면 quality가 2 감소한다")
        void decreaseQualityTwiceAfterSellDate() {
            Item item = new Item(
                    NORMAL_ITEM,
                    0,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(-1, item.sellIn);
            assertEquals(ANY_PLUS_NUM - 2, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 지나도 quality는 0보다 작아지지 않는다")
        void expiredItemQualityNeverGoesBelowZero() {
            Item item = new Item(
                    NORMAL_ITEM,
                    MINUS_NUM,
                    0
            );

            updateQuality(item);

            assertEquals(MINUS_NUM - 1, item.sellIn);
            assertEquals(0, item.quality);
        }
    }


    @Nested
    @DisplayName("전설 아이템 Sulfuras")
    class SulfurasTest {

        @Test
        @DisplayName("판매 기한 전에도 sellIn과 quality가 변하지 않는다")
        void neverChangesBeforeSellDate() {
            Item item = new Item(
                    SULFURAS,
                    ANY_PLUS_NUM,
                    LEGEND_PRICE_NUM
            );

            updateQuality(item);

            assertEquals(ANY_PLUS_NUM, item.sellIn);
            assertEquals(LEGEND_PRICE_NUM, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 지나도 sellIn과 quality가 변하지 않는다")
        void neverChangesAfterSellDate() {
            Item item = new Item(
                    SULFURAS,
                    MINUS_NUM,
                    LEGEND_PRICE_NUM
            );

            updateQuality(item);

            assertEquals(MINUS_NUM, item.sellIn);
            assertEquals(LEGEND_PRICE_NUM, item.quality);
        }
    }


    @Nested
    @DisplayName("Aged Brie")
    class AgedBrieTest {

        @Test
        @DisplayName("판매 기한 전에는 quality가 1 증가한다")
        void increaseQualityBeforeSellDate() {
            Item item = new Item(
                    AGED_BRIE,
                    ANY_PLUS_NUM,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(ANY_PLUS_NUM - 1, item.sellIn);
            assertEquals(ANY_PLUS_NUM + 1, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 지나면 quality가 2 증가한다")
        void increaseQualityTwiceAfterSellDate() {
            Item item = new Item(
                    AGED_BRIE,
                    0,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(-1, item.sellIn);
            assertEquals(ANY_PLUS_NUM + 2, item.quality);
        }

        @Test
        @DisplayName("quality는 최대 50을 넘지 않는다")
        void qualityNeverExceedsMaxQuality() {
            Item item = new Item(
                    AGED_BRIE,
                    ANY_PLUS_NUM,
                    MAX_QUALITY
            );

            updateQuality(item);

            assertEquals(ANY_PLUS_NUM - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 지나도 quality는 최대 50을 넘지 않는다")
        void expiredQualityNeverExceedsMaxQuality() {
            Item item = new Item(
                    AGED_BRIE,
                    0,
                    MAX_QUALITY
            );

            updateQuality(item);

            assertEquals(-1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }
    }


    @Nested
    @DisplayName("Backstage Pass")
    class BackstagePassTest {

        @Test
        @DisplayName("판매 기한이 10일보다 많이 남으면 quality가 1 증가한다")
        void increaseQualityByOneWhenMoreThanTenDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BIGGER_THAN_10,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(BIGGER_THAN_10 - 1, item.sellIn);
            assertEquals(ANY_PLUS_NUM + 1, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 6~10일 남으면 quality가 2 증가한다")
        void increaseQualityByTwoWhenSixToTenDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_10_TO_6,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(BETWEEN_10_TO_6 - 1, item.sellIn);
            assertEquals(ANY_PLUS_NUM + 2, item.quality);
        }

        @Test
        @DisplayName("판매 기한이 1~5일 남으면 quality가 3 증가한다")
        void increaseQualityByThreeWhenOneToFiveDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_5_TO_1,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(BETWEEN_5_TO_1 - 1, item.sellIn);
            assertEquals(ANY_PLUS_NUM + 3, item.quality);
        }

        @Test
        @DisplayName("공연 날짜가 지나면 quality가 0이 된다")
        void qualityBecomesZeroAfterConcert() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    0,
                    ANY_PLUS_NUM
            );

            updateQuality(item);

            assertEquals(-1, item.sellIn);
            assertEquals(0, item.quality);
        }

        @Test
        @DisplayName("10일보다 많이 남아도 quality는 50을 넘지 않는다")
        void qualityNeverExceedsMaxQualityWhenMoreThanTenDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BIGGER_THAN_10,
                    MAX_QUALITY
            );

            updateQuality(item);

            assertEquals(BIGGER_THAN_10 - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }

        @Test
        @DisplayName("6~10일 남아도 quality는 50을 넘지 않는다")
        void qualityNeverExceedsMaxQualityWhenSixToTenDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_10_TO_6,
                    MAX_QUALITY
            );

            updateQuality(item);

            assertEquals(BETWEEN_10_TO_6 - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }

        @Test
        @DisplayName("1~5일 남아도 quality는 50을 넘지 않는다")
        void qualityNeverExceedsMaxQualityWhenOneToFiveDaysRemain() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_5_TO_1,
                    MAX_QUALITY
            );

            updateQuality(item);

            assertEquals(BETWEEN_5_TO_1 - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }

        @Test
        @DisplayName("quality가 49이고 1~5일 남아도 최대값은 50이다")
        void qualityStopsAtMaxFromFortyNine() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_5_TO_1,
                    MAX_QUALITY - 1
            );

            updateQuality(item);

            assertEquals(BETWEEN_5_TO_1 - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }

        @Test
        @DisplayName("quality가 48이고 1~5일 남으면 quality가 50이 된다")
        void qualityStopsAtMaxFromFortyEight() {
            Item item = new Item(
                    BACKSTAGE_PASS,
                    BETWEEN_5_TO_1,
                    MAX_QUALITY - 2
            );

            updateQuality(item);

            assertEquals(BETWEEN_5_TO_1 - 1, item.sellIn);
            assertEquals(MAX_QUALITY, item.quality);
        }
    }


    private void updateQuality(Item item) {
        GildedRose gildedRose =
                new GildedRose(new Item[]{item});

        gildedRose.updateQuality();
    }
}

