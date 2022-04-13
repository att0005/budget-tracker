package com.budget.app.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AccountServiceTest.class,
    BudgetServiceTest.class, CategoryServiceTest.class, TransactionServiceTest.class, UserServiceTest.class
})
public class TestSuiteService {

}
