import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AgeCodeSet from './age-code-set';
import AgeCodeSetDetail from './age-code-set-detail';
import AgeCodeSetUpdate from './age-code-set-update';
import AgeCodeSetDeleteDialog from './age-code-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AgeCodeSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AgeCodeSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AgeCodeSetDetail} />
      <ErrorBoundaryRoute path={match.url} component={AgeCodeSet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AgeCodeSetDeleteDialog} />
  </>
);

export default Routes;
