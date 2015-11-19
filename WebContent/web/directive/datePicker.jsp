<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<p class="input-group">
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
				ng-change="selectDate()"/>
			<span class="input-group-btn">
				<button type="button" class="btn btn-default" ng-click="dateOpened = true">
					<i class="glyphicon glyphicon-calendar"></i>
				</button>
			</span>
		</p>
</div>
