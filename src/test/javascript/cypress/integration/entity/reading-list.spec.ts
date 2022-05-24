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

describe('ReadingList e2e test', () => {
  const readingListPageUrl = '/reading-list';
  const readingListPageUrlPattern = new RegExp('/reading-list(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const readingListSample = {};

  let readingList: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/reading-lists+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/reading-lists').as('postEntityRequest');
    cy.intercept('DELETE', '/api/reading-lists/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (readingList) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/reading-lists/${readingList.id}`,
      }).then(() => {
        readingList = undefined;
      });
    }
  });

  it('ReadingLists menu should load ReadingLists page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('reading-list');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ReadingList').should('exist');
    cy.url().should('match', readingListPageUrlPattern);
  });

  describe('ReadingList page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(readingListPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ReadingList page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/reading-list/new$'));
        cy.getEntityCreateUpdateHeading('ReadingList');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', readingListPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/reading-lists',
          body: readingListSample,
        }).then(({ body }) => {
          readingList = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/reading-lists+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/reading-lists?page=0&size=20>; rel="last",<http://localhost/api/reading-lists?page=0&size=20>; rel="first"',
              },
              body: [readingList],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(readingListPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ReadingList page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('readingList');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', readingListPageUrlPattern);
      });

      it('edit button click should load edit ReadingList page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ReadingList');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', readingListPageUrlPattern);
      });

      it('last delete button click should delete instance of ReadingList', () => {
        cy.intercept('GET', '/api/reading-lists/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('readingList').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', readingListPageUrlPattern);

        readingList = undefined;
      });
    });
  });

  describe('new ReadingList page', () => {
    beforeEach(() => {
      cy.visit(`${readingListPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ReadingList');
    });

    it('should create an instance of ReadingList', () => {
      cy.get(`[data-cy="materialId"]`).type('experiences Regional lime').should('have.value', 'experiences Regional lime');

      cy.get(`[data-cy="created"]`).type('2022-05-17T22:19').blur().should('have.value', '2022-05-17T22:19');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        readingList = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', readingListPageUrlPattern);
    });
  });
});
