import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('SearchHistory e2e test', () => {
  const searchHistoryPageUrl = '/search-history';
  const searchHistoryPageUrlPattern = new RegExp('/search-history(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const searchHistorySample = {};

  let searchHistory: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/search-histories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/search-histories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/search-histories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (searchHistory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/search-histories/${searchHistory.id}`,
      }).then(() => {
        searchHistory = undefined;
      });
    }
  });

  it('SearchHistories menu should load SearchHistories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItemMulti('search-history');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SearchHistory').should('exist');
    cy.url().should('match', searchHistoryPageUrlPattern);
  });

  describe('SearchHistory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(searchHistoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SearchHistory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/search-history/new$'));
        cy.getEntityCreateUpdateHeading('SearchHistory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchHistoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/search-histories',
          body: searchHistorySample,
        }).then(({ body }) => {
          searchHistory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/search-histories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/search-histories?page=0&size=20>; rel="last",<http://localhost/api/search-histories?page=0&size=20>; rel="first"',
              },
              body: [searchHistory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(searchHistoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SearchHistory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('searchHistory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchHistoryPageUrlPattern);
      });

      it('edit button click should load edit SearchHistory page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SearchHistory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchHistoryPageUrlPattern);
      });

      it('last delete button click should delete instance of SearchHistory', () => {
        cy.intercept('GET', '/api/search-histories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('searchHistory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchHistoryPageUrlPattern);

        searchHistory = undefined;
      });
    });
  });

  describe('new SearchHistory page', () => {
    beforeEach(() => {
      cy.visit(`${searchHistoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SearchHistory');
    });

    it('should create an instance of SearchHistory', () => {
      cy.get(`[data-cy="searchTerm"]`).type('Beauty').should('have.value', 'Beauty');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        searchHistory = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', searchHistoryPageUrlPattern);
    });
  });
});
