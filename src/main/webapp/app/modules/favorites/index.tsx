import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Favorites from "app/modules/favorites/favorites";


const Routes = ({ match }) => (
  <>
    <ErrorBoundaryRoute exact path={`${match.url}/favorites`} component={Favorites} />
  </>
);

export default Routes;
