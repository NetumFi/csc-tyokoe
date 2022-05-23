import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './education-level-code-set.reducer';

export const EducationLevelCodeSetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const educationLevelCodeSetEntity = useAppSelector(state => state.educationLevelCodeSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="educationLevelCodeSetDetailsHeading">
          <Translate contentKey="csc2022App.educationLevelCodeSet.detail.title">EducationLevelCodeSet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="csc2022App.educationLevelCodeSet.code">Code</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.code}</dd>
          <dt>
            <span id="codeId">
              <Translate contentKey="csc2022App.educationLevelCodeSet.codeId">Code Id</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.codeId}</dd>
          <dt>
            <span id="labelEn">
              <Translate contentKey="csc2022App.educationLevelCodeSet.labelEn">Label En</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.labelEn}</dd>
          <dt>
            <span id="labelFi">
              <Translate contentKey="csc2022App.educationLevelCodeSet.labelFi">Label Fi</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.labelFi}</dd>
          <dt>
            <span id="labelSv">
              <Translate contentKey="csc2022App.educationLevelCodeSet.labelSv">Label Sv</Translate>
            </span>
          </dt>
          <dd>{educationLevelCodeSetEntity.labelSv}</dd>
        </dl>
        <Button tag={Link} to="/education-level-code-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/education-level-code-set/${educationLevelCodeSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EducationLevelCodeSetDetail;
