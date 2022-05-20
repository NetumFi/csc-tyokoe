import axios from 'axios';
import {createAsyncThunk, createSlice, PayloadAction} from '@reduxjs/toolkit';

import { serializeAxiosError } from 'app/shared/reducers/reducer.utils';

const initialState = {
  searchTerms: null,
  filters: [],
  loading: false
};

export type SearchState = Readonly<typeof initialState>;

// Actions

export const handleSearch = createAsyncThunk(
  'search-material/fetch-data',
  async (data: { searchTerms: string; filters: string[] }) => axios.post<any>('api/search', data),
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
    deleteFilter(state, action: PayloadAction<string>) {
      state.filters.splice(state.filters.indexOf(action.payload), 1);
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
      .addCase(handleSearch.fulfilled, (state) => {
          state.loading = false;
      });
  },
});

export const { reset, addFilter, deleteFilter } = SearchMaterialSlice.actions;

// Reducer
export default SearchMaterialSlice.reducer;
