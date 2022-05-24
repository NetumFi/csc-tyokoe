import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserResultKeyword from './user-result-keyword';
import UserResultKeywordDetail from './user-result-keyword-detail';
import UserResultKeywordUpdate from './user-result-keyword-update';
import UserResultKeywordDeleteDialog from './user-result-keyword-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserResultKeywordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserResultKeywordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserResultKeywordDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserResultKeyword} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserResultKeywordDeleteDialog} />
  </>
);

export default Routes;
