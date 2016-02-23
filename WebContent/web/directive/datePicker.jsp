<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<span class="input-group" style="width: 100px;">
		<input 	type="text" class="form-control" 
			datepicker-popup="yyyy-MM-dd" 
			ng-model="selectedValue" 
			is-open="dateOpened" 
			min-date="'20000101'" 
			max-date="maxDate" 
			init-date="maxDate"
			datepicker-options="dateOptions" 
			date-disabled="disabled(date, mode)" 
			ng-required="true"  close-text="Close" 
			ng-change="selectDate()"
			style="height: 25px;width: 120px;"/>
		<span class="input-group-btn">
			<button type="button" class="btn btn-default" ng-click="dateOpened = true" style="height: 25px;padding: 3px;">
				<i class="glyphicon glyphicon-calendar"></i>
			</button>
		</span>
	</span>

