package com.budget.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.budget.app.entity.Budget;
import com.budget.app.model.BudgetDTO;

@RunWith(Parameterized.class)
public class BudgetServiceParamTest {
	
	private BudgetService service;
	private BudgetDTO dto;
	

	
	public BudgetServiceParamTest(BudgetDTO dto) {
		super();
		this.dto = dto;
	}

	@Before
	public void initialize() {
		service = new BudgetService();
	}
	
	@Test
	public void testBudgetCreation() {
		System.out.println(dto.getUserId());
		assertEquals(null, service.saveBudget(dto));	
	}
	
	@Parameterized.Parameters
	public static Collection getData() {
		
		BudgetDTO budgetDTO1 = new BudgetDTO(200, 200, 10.0, 10.0, "April","2022");
		BudgetDTO budgetDTO2 = new BudgetDTO(300, 300, 10.0, 10.0, "April","2022");
		List<BudgetDTO> list = new ArrayList<>();
		list.add(budgetDTO1);
		list.add(budgetDTO2);
		return list;
	}

}
