import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reading-list.reducer';

export const ReadingListDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const readingListEntity = useAppSelector(state => state.readingList.entity);

  const accountComponent =  account && account.authorities.includes('ROLE_ADMIN') ? '/reading-list' : '/favorites'

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="readingListDetailsHeading">
          <Translate contentKey="csc2022App.readingList.detail.title">ReadingList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{readingListEntity.id}</dd>
          <dt>
            <span id="materialId">
              <Translate contentKey="csc2022App.readingList.materialId">Material Id</Translate>
            </span>
          </dt>
          <dd>{readingListEntity.materialId}</dd>
          <dt>
            <span id="created">
              <Translate contentKey="csc2022App.readingList.created">Created</Translate>
            </span>
          </dt>
          <dd>
            {readingListEntity.created ? <TextFormat value={readingListEntity.created} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="csc2022App.readingList.user">User</Translate>
          </dt>
          <dd>{readingListEntity.user ? readingListEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to={accountComponent} replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        {account && account.authorities.includes('ROLE_ADMIN') ?
        <Button tag={Link} to={`/reading-list/${readingListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button> : <></>
        }
      </Col>
    </Row>
  );
};

export default ReadingListDetail;
