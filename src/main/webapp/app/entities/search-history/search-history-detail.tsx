import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './search-history.reducer';

export const SearchHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const searchHistoryEntity = useAppSelector(state => state.searchHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="searchHistoryDetailsHeading">
          <Translate contentKey="csc2022App.searchHistory.detail.title">SearchHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{searchHistoryEntity.id}</dd>
          <dt>
            <span id="searchTerm">
              <Translate contentKey="csc2022App.searchHistory.searchTerm">Search Term</Translate>
            </span>
          </dt>
          <dd>{searchHistoryEntity.searchTerm}</dd>
          <dt>
            <Translate contentKey="csc2022App.searchHistory.user">User</Translate>
          </dt>
          <dd>{searchHistoryEntity.user ? searchHistoryEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/search-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/search-history/${searchHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SearchHistoryDetail;
