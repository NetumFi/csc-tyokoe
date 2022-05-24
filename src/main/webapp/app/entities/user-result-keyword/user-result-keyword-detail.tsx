import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-result-keyword.reducer';

export const UserResultKeywordDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userResultKeywordEntity = useAppSelector(state => state.userResultKeyword.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userResultKeywordDetailsHeading">
          <Translate contentKey="csc2022App.userResultKeyword.detail.title">UserResultKeyword</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userResultKeywordEntity.id}</dd>
          <dt>
            <span id="resultKeyword">
              <Translate contentKey="csc2022App.userResultKeyword.resultKeyword">Result Keyword</Translate>
            </span>
          </dt>
          <dd>{userResultKeywordEntity.resultKeyword}</dd>
          <dt>
            <Translate contentKey="csc2022App.userResultKeyword.user">User</Translate>
          </dt>
          <dd>{userResultKeywordEntity.user ? userResultKeywordEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-result-keyword" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-result-keyword/${userResultKeywordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserResultKeywordDetail;
