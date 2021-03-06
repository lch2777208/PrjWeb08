package org.zerock.bank;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by macbookpro on 2017. 3. 26. PM 10:33
 * Project / org.zerock.bank
 * No pain, No gain!
 * What : 계좌 클래스(Account)에 대한 테스트 케이스 작성을 위해 만든 테스트 클래스.
 * Why :
 * How :
 */


public class AccountTest {

    private Account account;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        account = new Account(10000);
    }

    /**
     * Test account.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAccount() throws Exception {

    }

    /**
     * Test get balance.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetBalance() throws Exception {
        assertEquals(10000, account.getBalance());

        account = new Account(1000);
        assertEquals(1000, account.getBalance());

        account = new Account(0);
        assertEquals(0, account.getBalance());
    }

    /**
     * Test deposit.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeposit() throws Exception {
        account.deposit(1000);
        assertEquals(11000, account.getBalance());
    }

    /**
     * Test widthdraw.
     *
     * @throws Exception the exception
     */
    @Test
    public void testWidthdraw() throws Exception {
        account.withdraw(1000);
        assertEquals(9000, account.getBalance());
    }

}
