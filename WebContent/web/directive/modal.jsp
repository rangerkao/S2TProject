<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <div class="modal fade">
 	<div class="modal-dialog" style="width:{{modalWidth}}">
 		<div class="modal-content">
 			<div class="modal-header">
 				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
 				<h4 class="modal-title">{{ title }}</h4>
			</div>
			<div class="modal-body" ng-transclude>
			</div>
		</div>
	</div>
</div>
		