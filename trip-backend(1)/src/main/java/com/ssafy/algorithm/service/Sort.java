package com.ssafy.algorithm.service;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.board.dto.Board;

public class Sort {
	private static Sort instance = new Sort();
	public static Sort getInstance() {
		return instance;
	}
	private Sort() {}
	
    public List<Board> sortByRegister(List<Board> list) {
        mergeSortRegister(list, 0, list.size() - 1);
        return list;
    }

    private void mergeSortRegister(List<Board> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortRegister(list, left, mid);
            mergeSortRegister(list, mid + 1, right);
            mergeRegister(list, left, mid, right);
        }
    }

    private void mergeRegister(List<Board> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        List<Board> L = new ArrayList<>(n1);
        List<Board> R = new ArrayList<>(n2);
        for (int i = 0; i < n1; ++i)
            L.add(list.get(left + i));
        for (int j = 0; j < n2; ++j)
            R.add(list.get(mid + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L.get(j).getRegisterTime().compareTo(R.get(i).getRegisterTime()) >= 0) {
                list.set(k, L.get(i));
                i++;
            } else {
                list.set(k, R.get(j));
                j++;
            }
            k++;
        }
        while (i < n1) {
            list.set(k, L.get(i));
            i++;
            k++;
        }
        while (j < n2) {
            list.set(k, R.get(j));
            j++;
            k++;
        }
    }

    public List<Board> sortByPopularity(List<Board> list) {
        mergeSortPopularity(list, 0, list.size() - 1);
        return list;
    }

    private void mergeSortPopularity(List<Board> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSortPopularity(list, left, mid);
            mergeSortPopularity(list, mid + 1, right);

            mergePopularity(list, left, mid, right);
        }
    }

    private void mergePopularity(List<Board> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Board> L = new ArrayList<>(n1);
        List<Board> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; ++i)
            L.add(list.get(left + i));
        for (int j = 0; j < n2; ++j)
            R.add(list.get(mid + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L.get(i).getHit() >= R.get(j).getHit()) {
                list.set(k, L.get(i));
                i++;
            } else {
                list.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, R.get(j));
            j++;
            k++;
        }
    }

    public List<Board> sortByAlpha(List<Board> list) {
        mergeSortAlpha(list, 0, list.size() - 1);
        return list;
    }

    private void mergeSortAlpha(List<Board> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSortAlpha(list, left, mid);
            mergeSortAlpha(list, mid + 1, right);

            mergeAlpha(list, left, mid, right);
        }
    }

    private void mergeAlpha(List<Board> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Board> L = new ArrayList<>(n1);
        List<Board> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; ++i)
            L.add(list.get(left + i));
        for (int j = 0; j < n2; ++j)
            R.add(list.get(mid + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L.get(i).getSubject().compareToIgnoreCase(R.get(j).getSubject()) <= 0) {
                list.set(k, L.get(i));
                i++;
            } else {
                list.set(k, R.get(j));
                j++;
            }
            k++;
        }
        while (i < n1) {
            list.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, R.get(j));
            j++;
            k++;
        }
    }
}
