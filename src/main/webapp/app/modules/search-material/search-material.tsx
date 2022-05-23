import './search-material.scss';

import React, {useEffect} from 'react';
import {translate, Translate, ValidatedForm} from 'react-jhipster';
import {
  Row,
  Col,
  Button,
  Input,
  Badge,
  Container
} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {handleSearch, addFilter, deleteFilter} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {getEntities} from "app/entities/education-level-code-set/education-level-code-set.reducer";


export const SearchMaterial = (props) => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const educationLevelCodeSets = useAppSelector(state => state.educationLevelCodeSet);
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntities({page: 0, size: 100, sort: null}));
  }, [props.location]);

  const filterItems = educationLevelCodeSets.entities;

  const handleValidSubmit = ({searchterms, filter}) => {
    dispatch(handleSearch({searchTerms: searchterms, filters: filter}));
  };

  const handleAddFilter = (params) => {
    const rajaus = filterItems.filter(i => i.codeId === params.target.value)[0];
    dispatch(addFilter(rajaus));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const codeLocales = {"fi": "labelFi", "en": "labelEn", "sv": "labelSv"};

  const sort = ["Suosituin ensin"];

  return (
    <Container>
        <h2>
          <Translate contentKey="materialsearch.title.welcome" interpolate={{name: account?.firstName}}>
            Tervetuloa, {account?.firstName}.
          </Translate>
        </h2>
        <Container>
        <ValidatedForm id="materialsearch-form" onSubmit={handleValidSubmit} defaultValues={searchparams}>
          <Row>
            <Col sm="10">
              <Input
                type="text"
                name="searchterms"
                label={translate('materialsearch.form.searchterms')}
                id="searchterms"
                placeholder={translate('materialsearch.form.searchterms-placeholder')}
                data-cy="searchterms"
              />
            </Col>
            <Col xs="1">
              <Button color="primary" type="submit" data-cy="submit">
                <Translate contentKey="materialsearch.form.button">Search</Translate>
              </Button>
            </Col>
          </Row>
          <Row>
            <Col
              lg="3">
              <Input
                name="filter"
                label={translate('materialsearch.form.filter')}
                id="filter"
                placeholder={translate('materialsearch.form.filter')}
                type="select"
                data-cy="filter"
                onChange={handleAddFilter}
                defaultValue={null}>
                {
                  [<option key="firstElement"> {translate('materialsearch.form.filter')} </option>]
                    .concat(filterItems.filter(item => !searchparams.filters.includes(item)).map(filterItem => (
                      <option value={filterItem.codeId} key={filterItem.codeId}>
                        {filterItem[codeLocales[currentLocale]]}
                      </option>
                    )))
                }
              </Input>
            </Col>
            <Col sm="9" className="filterList">
              {searchparams.filters.map(filterItem =>
                <Badge
                  key={filterItem.codeId}
                  className="filterChip"
                  tabIndex={0}
                  color="primary"
                  onClick={() => handleDeleteFilter(filterItem)}
                  pill>
                  {filterItem["labelFi"]}
                  <span className="icon">
                    <FontAwesomeIcon icon="x"/>
                  </span>
                </Badge>)}
            </Col>
          </Row>
          <Row>
            <Col sm="3">
              <Input
                type="select">
                {sort.map(s => <option key={s}>{s}</option>)}
              </Input>
            </Col>
          </Row>
        </ValidatedForm>
        </Container>
    </Container>
  );
};

export default SearchMaterial;
