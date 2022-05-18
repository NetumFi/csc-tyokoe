import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EducationLevelCodeSet from './education-level-code-set';
import EducationLevelCodeSetDetail from './education-level-code-set-detail';
import EducationLevelCodeSetUpdate from './education-level-code-set-update';
import EducationLevelCodeSetDeleteDialog from './education-level-code-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EducationLevelCodeSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EducationLevelCodeSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EducationLevelCodeSetDetail} />
      <ErrorBoundaryRoute path={match.url} component={EducationLevelCodeSet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EducationLevelCodeSetDeleteDialog} />
  </>
);

export default Routes;
