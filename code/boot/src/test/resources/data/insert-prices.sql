/*BRAND_ID         START_DATE                                    END_DATE                        PRICE_LIST                   PRODUCT_ID  PRIORITY                 PRICE           CURR
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
1                   2020-06-14-00.00.00                        2020-12-31-23.59.59                1                           35455       0                         35.50           EUR
1                   2020-06-14-15.00.00                        2020-06-14-18.30.00                2                           35455       1                         25.45           EUR
1                   2020-06-15-00.00.00                        2020-06-15-11.00.00                3                           35455       1                         30.50           EUR
1                   2020-06-15-16.00.00                        2020-12-31-23.59.59                4                           35455       1                         38.95           EUR*/

INSERT INTO PRICES(ID, BRAND_ID, PRODUCT_ID, PRICE, START_DATE, END_DATE, PRIORITY)
  VALUES('00000000-0000-0000-0000-000000000001', 1, 35455, 35.50, PARSEDATETIME('2020-06-14-00.00.00', 'yyyy-MM-dd-HH.mm.ss'), PARSEDATETIME('2020-12-31-23.59.59', 'yyyy-MM-dd-HH.mm.ss'), 0);
INSERT INTO PRICES(ID, BRAND_ID, PRODUCT_ID, PRICE, START_DATE, END_DATE, PRIORITY)
  VALUES('00000000-0000-0000-0000-000000000002', 1, 35455, 25.45, PARSEDATETIME('2020-06-14-15.00.00', 'yyyy-MM-dd-HH.mm.ss'), PARSEDATETIME('2020-06-14-18.30.00', 'yyyy-MM-dd-HH.mm.ss'), 1);
INSERT INTO PRICES(ID, BRAND_ID, PRODUCT_ID, PRICE, START_DATE, END_DATE, PRIORITY)
  VALUES('00000000-0000-0000-0000-000000000003', 1, 35455, 30.50, PARSEDATETIME('2020-06-15-00.00.00', 'yyyy-MM-dd-HH.mm.ss'), PARSEDATETIME('2020-06-15-11.00.00', 'yyyy-MM-dd-HH.mm.ss'), 1);
INSERT INTO PRICES(ID, BRAND_ID, PRODUCT_ID, PRICE, START_DATE, END_DATE, PRIORITY)
  VALUES('00000000-0000-0000-0000-000000000004', 1, 35455, 38.95, PARSEDATETIME('2020-06-15-16.00.00', 'yyyy-MM-dd-HH.mm.ss'), PARSEDATETIME('2020-12-31-23.59.59', 'yyyy-MM-dd-HH.mm.ss'), 1);

