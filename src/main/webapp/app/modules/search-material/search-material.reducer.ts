import axios from 'axios';
import {createAsyncThunk, createSlice, PayloadAction, current} from '@reduxjs/toolkit';

import { serializeAxiosError } from 'app/shared/reducers/reducer.utils';

const initialState = {
  searchTerms: null,
  filters: [],
  loading: false,
  material: {hits: null, results: []}
};

export type SearchState = Readonly<typeof initialState>;

// Actions
export const handleSearch = createAsyncThunk(
  'search-material/fetch-data',
  async (data: { searchterms: string }, { getState }) => {
    const {searchTerms, filters} = (getState() as any).searchMaterial as SearchState;
    const postData = {
      keywords: searchTerms,
      filters: [{
        filter: "educationalLevels",
        values: (filters || []).map(i => i.codeId)
      }],
      paging: {
        from: 0,
        size: 10,
        sort: "relevance"
      }
    };
    return axios.post<any>('api/search/do-search', postData);
  },
  { serializeError: serializeAxiosError }
);


export const SearchMaterialSlice = createSlice({
  name: 'searchMaterial',
  initialState: initialState as SearchState,
  reducers: {
    reset() {
      return initialState;
    },
    addFilter(state, action: PayloadAction<string>) {
      state.filters.push(action.payload);
    },
    deleteFilter(state, action: PayloadAction<any>) {
      state.filters = state.filters.filter(i => i.codeId !== action.payload.codeId);
    },
    setSearchTerms(state, action: PayloadAction<string>) {
      state.searchTerms = action.payload;
    }
  },
  extraReducers(builder) {
    builder
      .addCase(handleSearch.pending, state => {
        state.loading = true;
      })
      .addCase(handleSearch.rejected, (state, action) => {
        state.loading = false;
        // state.errorMessage = action.error.message;
      })
      .addCase(handleSearch.fulfilled, (state, action) => {
          state.loading = false;
          state.material = action.payload.data;
      });
  },
});

export const { reset, addFilter, deleteFilter, setSearchTerms } = SearchMaterialSlice.actions;

// Reducer
export default SearchMaterialSlice.reducer;
