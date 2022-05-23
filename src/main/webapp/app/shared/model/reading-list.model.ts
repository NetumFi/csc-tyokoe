import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IReadingList {
  id?: number;
  materialId?: string | null;
  created?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IReadingList> = {};
